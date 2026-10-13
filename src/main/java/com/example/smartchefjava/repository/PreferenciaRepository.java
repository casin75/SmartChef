package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PreferenciaRepository extends JpaRepository<Preferencia, Integer> {

    List<Preferencia> findByTipo(Integer tipo);

    @Query("SELECT p FROM Preferencia p LEFT JOIN FETCH p.ingredientes WHERE p.id = :id")
    Optional<Preferencia> buscarConIngredientes(@Param("id") Integer id);
}