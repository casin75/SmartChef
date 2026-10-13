package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Paso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasoRepository extends JpaRepository<Paso, Integer> {

    List<Paso> findByRecetaIdOrderByNumeroAsc(Integer recetaId);

    long countByRecetaId(Integer recetaId);

    boolean existsByRecetaIdAndNumero(Integer recetaId, Integer numero);
}