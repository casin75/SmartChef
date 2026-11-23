package com.example.smartchefjava.controller;

import com.example.smartchefjava.dto.request.RecetaRequest;
import com.example.smartchefjava.dto.response.PaginaResponse;
import com.example.smartchefjava.dto.response.RecetaDetalleResponse;
import com.example.smartchefjava.dto.response.RecetaResumenResponse;
import com.example.smartchefjava.service.RecetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

// Capa fina: recibe, llama al servicio y devuelve con su código. Ni un if de negocio.
@Tag(name = "Recetas", description = "Catálogo de recetas")
@RestController
@RequestMapping("/api/v1/recetas")
@RequiredArgsConstructor
@Validated
public class RecetaController {

    private final RecetaService recetaService;

    @Operation(summary = "Listar recetas, con buscador por nombre y paginación",
            description = "Ejemplo: ?nombre=pasta&page=0&size=10&sort=tiempo,asc")
    @GetMapping
    public PaginaResponse<RecetaResumenResponse> listar(
            @Parameter(description = "Texto que debe contener el nombre", example = "tortilla")
            @RequestParam(required = false) String nombre,
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        return recetaService.listar(nombre, pageable);
    }

    @Operation(summary = "Ficha completa de una receta: pasos, ingredientes y fotos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receta encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe esa receta", content = @Content)
    })
    @GetMapping("/{id}")
    public RecetaDetalleResponse buscar(
            @Parameter(description = "Id de la receta", example = "3")
            @PathVariable @Positive(message = "El id tiene que ser positivo") Integer id) {
        return recetaService.buscarPorId(id);
    }

    @Operation(summary = "Crear una receta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Receta creada"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Algún ingrediente no existe", content = @Content),
            @ApiResponse(responseCode = "409", description = "Ya existe una receta con ese nombre", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RecetaDetalleResponse> crear(@Valid @RequestBody RecetaRequest peticion) {
        RecetaDetalleResponse creada = recetaService.crear(peticion);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.id())
                .toUri();

        return ResponseEntity.created(ubicacion).body(creada);   // 201 + cabecera Location
    }

    @Operation(summary = "Editar una receta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receta actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "No existe la receta o algún ingrediente", content = @Content),
            @ApiResponse(responseCode = "409", description = "Otra receta ya usa ese nombre", content = @Content)
    })
    @PutMapping("/{id}")
    public RecetaDetalleResponse actualizar(@PathVariable @Positive(message = "El id tiene que ser positivo") Integer id,
                                            @Valid @RequestBody RecetaRequest peticion) {
        return recetaService.actualizar(id, peticion);
    }

    @Operation(summary = "Eliminar una receta")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Receta eliminada"),
            @ApiResponse(responseCode = "404", description = "No existe esa receta", content = @Content),
            @ApiResponse(responseCode = "409", description = "La receta ya está en el historial de alguien", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Positive(message = "El id tiene que ser positivo") Integer id) {
        recetaService.eliminar(id);
    }
}
