package com.example.smartchefjava.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

// Un único sitio para todos los errores: todos salen con el mismo formato (ProblemDetail, RFC 9457)
@RestControllerAdvice
@Slf4j
public class ManejadorGlobalErrores {

    // ---------- Nuestras excepciones ----------

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail noEncontrado(RecursoNoEncontradoException e) {
        return construir(HttpStatus.NOT_FOUND, "Recurso no encontrado", e.getMessage());
    }

    @ExceptionHandler(ConflictoException.class)
    public ProblemDetail conflicto(ConflictoException e) {
        return construir(HttpStatus.CONFLICT, "Conflicto con los datos existentes", e.getMessage());
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ProblemDetail reglaNegocio(ReglaNegocioException e) {
        return construir(HttpStatus.UNPROCESSABLE_CONTENT, "Regla de negocio incumplida", e.getMessage());
    }

    @ExceptionHandler(PeticionIncorrectaException.class)
    public ProblemDetail peticionIncorrecta(PeticionIncorrectaException e) {
        return construir(HttpStatus.BAD_REQUEST, "Petición incorrecta", e.getMessage());
    }

    // ---------- Validación de los DTOs (@Valid), campo a campo ----------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validacion(MethodArgumentNotValidException e) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errores.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        ProblemDetail problema = construir(HttpStatus.BAD_REQUEST, "Datos no válidos", "Revisa los campos indicados");
        problema.setProperty("errores", errores);
        return problema;
    }

    // Parámetros sueltos validados con @Validated en la clase (@Positive en un @PathVariable…)
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail validacionParametros(ConstraintViolationException e) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (ConstraintViolation<?> v : e.getConstraintViolations()) {
            String ruta = v.getPropertyPath().toString();               // "buscar.id"
            errores.putIfAbsent(ruta.substring(ruta.lastIndexOf('.') + 1), v.getMessage());
        }
        ProblemDetail problema = construir(HttpStatus.BAD_REQUEST, "Datos no válidos", "Revisa los parámetros indicados");
        problema.setProperty("errores", errores);
        return problema;
    }

    // Lo mismo cuando la valida Spring MVC directamente, sin @Validated en la clase
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail validacionMetodo(HandlerMethodValidationException e) {
        Map<String, String> errores = new LinkedHashMap<>();
        e.getParameterValidationResults().forEach(resultado -> {
            String nombre = resultado.getMethodParameter().getParameterName();
            resultado.getResolvableErrors()
                    .forEach(error -> errores.putIfAbsent(nombre, error.getDefaultMessage()));
        });
        ProblemDetail problema = construir(HttpStatus.BAD_REQUEST, "Datos no válidos", "Revisa los parámetros indicados");
        problema.setProperty("errores", errores);
        return problema;
    }

    // ---------- Otros casos que conviene cubrir ----------

    // JSON mal formado o un tipo que no se puede convertir ("tiempo": "mucho")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail jsonInvalido(HttpMessageNotReadableException e) {
        return construir(HttpStatus.BAD_REQUEST, "Petición no legible",
                "El cuerpo de la petición no tiene un formato válido");
    }

    // /recetas/abc cuando se espera un número
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tipoIncorrecto(MethodArgumentTypeMismatchException e) {
        return construir(HttpStatus.BAD_REQUEST, "Parámetro no válido",
                "El valor de '" + e.getName() + "' no tiene el tipo esperado");
    }

    // Ordenar por un campo que no existe (?sort=precio)
    @ExceptionHandler(PropertyReferenceException.class)
    public ProblemDetail ordenNoValido(PropertyReferenceException e) {
        return construir(HttpStatus.BAD_REQUEST, "Parámetro no válido",
                "No se puede ordenar por '" + e.getPropertyName() + "'");
    }

    // Una ruta que no existe
    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail rutaNoExiste(NoResourceFoundException e) {
        return construir(HttpStatus.NOT_FOUND, "Ruta no encontrada", "No existe la ruta /" + e.getResourcePath());
    }

    // Un verbo que la ruta no admite (DELETE /api/v1/recetas)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail metodoNoPermitido(HttpRequestMethodNotSupportedException e) {
        return construir(HttpStatus.METHOD_NOT_ALLOWED, "Método no permitido",
                "Esta ruta no admite " + e.getMethod());
    }

    // Una restricción de la base de datos que se nos ha escapado
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail integridad(DataIntegrityViolationException e) {
        log.warn("Violación de integridad", e);
        return construir(HttpStatus.CONFLICT, "Conflicto de integridad",
                "La operación viola una restricción de la base de datos");
    }

    // La red de seguridad: la traza va al log y al cliente le llega un mensaje neutro
    @ExceptionHandler(Exception.class)
    public ProblemDetail errorInesperado(Exception e) {
        log.error("Error no controlado", e);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", "Ha ocurrido un error inesperado");
    }

    private ProblemDetail construir(HttpStatus estado, String titulo, String detalle) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(estado, detalle);
        problema.setTitle(titulo);
        problema.setProperty("timestamp", LocalDateTime.now());
        return problema;
    }
}
