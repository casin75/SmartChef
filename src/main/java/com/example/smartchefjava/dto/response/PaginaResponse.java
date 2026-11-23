package com.example.smartchefjava.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

// Una página de resultados: la lista va dentro de "content", junto con los totales
public record PaginaResponse<T>(
        List<T> content,
        int number,          // página actual (empieza en 0)
        int size,            // elementos por página
        long totalElements,
        int totalPages,
        boolean last
) {
    public static <T> PaginaResponse<T> de(Page<T> pagina) {
        return new PaginaResponse<>(pagina.getContent(), pagina.getNumber(), pagina.getSize(),
                pagina.getTotalElements(), pagina.getTotalPages(), pagina.isLast());
    }
}
