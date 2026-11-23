package com.example.smartchefjava.dto.response;

import java.util.List;

// La ficha completa de una receta
public record RecetaDetalleResponse(
        Integer id,
        String nombre,
        String descripcion,
        Integer dificultad,
        Integer tiempo,
        Integer raciones,
        List<PasoResponse> pasos,
        List<IngredienteRecetaResponse> ingredientes,
        List<String> fotos   // aplanado: solo las direcciones
) {
}
