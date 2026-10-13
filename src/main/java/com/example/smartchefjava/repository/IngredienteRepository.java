package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Ingrediente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {

    Optional<Ingrediente> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);

    List<Ingrediente> findByPrecioLessThanOrderByPrecioAsc(BigDecimal precioMax);

    List<Ingrediente> findByKcalBetween(BigDecimal min, BigDecimal max);

    // Endpoint GET /ingredientes?texto= (paginado)
    Page<Ingrediente> findByNombreContainingIgnoreCase(String texto, Pageable pageable);
}