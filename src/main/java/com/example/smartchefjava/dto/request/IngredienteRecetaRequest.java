package com.example.smartchefjava.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

// Un ingrediente dentro de una receta: el cliente manda el id, no el objeto
public record IngredienteRecetaRequest(

        @Schema(description = "Id del ingrediente", example = "3")
        @NotNull(message = "Hay que indicar el ingrediente")
        Integer idIngrediente,

        @Schema(description = "Cantidad en la unidad del ingrediente", example = "500")
        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad tiene que ser mayor que 0")
        @DecimalMax(value = "999999.99", message = "La cantidad es demasiado grande")
        BigDecimal cantidad
) {
}
