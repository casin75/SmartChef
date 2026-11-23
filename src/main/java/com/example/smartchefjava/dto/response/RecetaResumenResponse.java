package com.example.smartchefjava.dto.response;

// Lo justo para pintar la tarjeta de una receta en el listado
public record RecetaResumenResponse(
        Integer id,
        String nombre,
        Integer dificultad,
        Integer tiempo,
        Integer raciones,
        String foto          // calculado: la primera foto, o null si no tiene
) {
}
