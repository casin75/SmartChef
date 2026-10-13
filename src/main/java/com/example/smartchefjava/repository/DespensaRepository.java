package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Despensa;
import com.example.smartchefjava.modelos.DespensaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface DespensaRepository extends JpaRepository<Despensa, DespensaId> {

    // ---------- Q1 · Derivadas (navegan usuario.id) ----------
    List<Despensa> findByUsuarioIdOrderByCaducidadAsc(Integer usuarioId);

    List<Despensa> findByUsuarioIdAndCaducidadLessThanEqual(Integer usuarioId, LocalDate limite);

    long countByUsuarioId(Integer usuarioId);

    // ---------- JPQL con JOIN FETCH ----------
    @Query("""
           SELECT d FROM Despensa d
           JOIN FETCH d.ingrediente
           WHERE d.usuario.id = :usuarioId
           ORDER BY d.caducidad ASC
           """)
    List<Despensa> buscarDespensaConIngredientes(@Param("usuarioId") Integer usuarioId);

    // Próximos a caducar (aviso de la tarea programada)
    @Query("""
           SELECT d FROM Despensa d
           JOIN FETCH d.ingrediente
           JOIN FETCH d.usuario
           WHERE d.caducidad BETWEEN :desde AND :hasta
           """)
    List<Despensa> buscarProximosACaducar(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    // ---------- Q4 · Modificación masiva ----------
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM Despensa d WHERE d.caducidad < :limite")
    int eliminarCaducadosAntesDe(@Param("limite") LocalDate limite);
}