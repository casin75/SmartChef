package com.example.smartchefjava.dto.request;

import com.example.smartchefjava.validacion.SinIngredientesRepetidos;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

// Lo que manda el cliente para crear o editar una receta
public record RecetaRequest(

        @Schema(description = "Nombre de la receta", example = "Tortilla de patatas")
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150, message = "El nombre no puede pasar de 150 caracteres")
        String nombre,

        @Schema(description = "Descripción corta", example = "Jugosa por dentro y dorada por fuera")
        @Size(max = 1000, message = "La descripción no puede pasar de 1000 caracteres")
        String descripcion,

        @Schema(description = "1 = fácil, 2 = media, 3 = difícil", example = "2")
        @NotNull(message = "La dificultad es obligatoria")
        @Min(value = 1, message = "La dificultad va de 1 (fácil) a 3 (difícil)")
        @Max(value = 3, message = "La dificultad va de 1 (fácil) a 3 (difícil)")
        Integer dificultad,

        @Schema(description = "Tiempo de preparación en minutos", example = "40")
        @NotNull(message = "El tiempo es obligatorio")
        @Positive(message = "El tiempo tiene que ser mayor que 0")
        @Max(value = 600, message = "El tiempo no puede pasar de 600 minutos")
        Integer tiempo,

        @Schema(description = "Número de raciones", example = "4")
        @NotNull(message = "Las raciones son obligatorias")
        @Min(value = 1, message = "Como mínimo 1 ración")
        @Max(value = 20, message = "Como máximo 20 raciones")
        Integer raciones,

        @Schema(description = "Pasos en orden; el número se pone solo")
        List<@NotBlank(message = "Un paso no puede estar vacío") String> pasos,

        @Schema(description = "Ingredientes con su cantidad")
        @SinIngredientesRepetidos
        List<@Valid IngredienteRecetaRequest> ingredientes,

        @Schema(description = "Direcciones de las fotos")
        List<@NotBlank(message = "La dirección de la foto no puede estar vacía") String> fotos
) {
}
