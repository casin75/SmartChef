package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNombre(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);

    List<Usuario> findByRol(Integer rol);

    long countByRol(Integer rol);

    // JOIN FETCH: usuario con sus preferencias (sin N+1)
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.preferencias WHERE u.id = :id")
    Optional<Usuario> buscarConPreferencias(@Param("id") Integer id);

    // JOIN FETCH: usuario con sus colecciones
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.colecciones WHERE u.id = :id")
    Optional<Usuario> buscarConColecciones(@Param("id") Integer id);
}