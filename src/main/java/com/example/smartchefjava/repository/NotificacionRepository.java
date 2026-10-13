package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Notificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    Page<Notificacion> findByTipo(Integer tipo, Pageable pageable);

    long countByTipo(Integer tipo);

    boolean existsByTextoContainingIgnoreCase(String texto);
}