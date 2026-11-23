package com.example.smartchefjava.dto.response;

import java.math.BigDecimal;

// Aplanado: el nombre del ingrediente en vez del objeto entero
public record IngredienteRecetaResponse(
        Integer idIngrediente,
        String nombre,
        BigDecimal cantidad,
        Integer unidadMedida
) {
}
