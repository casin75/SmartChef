package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Integer> {

    List<Foto> findByRecetaId(Integer recetaId);

    long countByRecetaId(Integer recetaId);
}
