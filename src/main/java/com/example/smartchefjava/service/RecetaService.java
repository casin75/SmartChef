package com.example.smartchefjava.service;

import com.example.smartchefjava.dto.request.RecetaRequest;
import com.example.smartchefjava.dto.response.PaginaResponse;
import com.example.smartchefjava.dto.response.RecetaDetalleResponse;
import com.example.smartchefjava.dto.response.RecetaResumenResponse;
import org.springframework.data.domain.Pageable;

// El contrato: qué se puede hacer con una receta
public interface RecetaService {

    PaginaResponse<RecetaResumenResponse> listar(String nombre, Pageable pageable);

    RecetaDetalleResponse buscarPorId(Integer id);

    RecetaDetalleResponse crear(RecetaRequest peticion);

    RecetaDetalleResponse actualizar(Integer id, RecetaRequest peticion);

    void eliminar(Integer id);
}
