package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ColeccionRepository extends JpaRepository<Coleccion, Integer> {

    List<Coleccion> findByUsuarioId(Integer usuarioId);

    Optional<Coleccion> findByUsuarioIdAndNombreIgnoreCase(Integer usuarioId, String nombre);

    boolean existsByUsuarioIdAndNombreIgnoreCase(Integer usuarioId, String nombre);

    // JOIN FETCH: colecciones del usuario con sus recetas (sin N+1)
    @Query("""
           SELECT DISTINCT c FROM Coleccion c
           LEFT JOIN FETCH c.recetas
           WHERE c.usuario.id = :usuarioId
           """)
    List<Coleccion> buscarConRecetas(@Param("usuarioId") Integer usuarioId);
}