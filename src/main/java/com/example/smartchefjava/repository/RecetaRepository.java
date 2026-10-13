package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Receta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    // ---------- Q1 · Derivadas ----------
    List<Receta> findByNombreContainingIgnoreCase(String texto);

    List<Receta> findByTiempoLessThanEqualOrderByTiempoAsc(Integer minutos);

    List<Receta> findByDificultadAndTiempoBetween(Integer dificultad, Integer min, Integer max);

    boolean existsByNombreIgnoreCase(String nombre);

    long countByDificultad(Integer dificultad);

    List<Receta> findTop5ByOrderByTiempoAsc();

    // ---------- Q6 · Paginación ----------
    Page<Receta> findByNombreContainingIgnoreCase(String texto, Pageable pageable);

    // ---------- Q2/Q8 · JPQL con JOIN FETCH (evita N+1) ----------
    // Carga 1: receta + pasos
    @Query("SELECT r FROM Receta r LEFT JOIN FETCH r.pasos WHERE r.id = :id")
    Optional<Receta> buscarDetalleConPasos(@Param("id") Integer id);

    // Carga 2: receta + ingredientes + ingrediente (dos consultas en vez de un producto cartesiano)
    @Query("""
           SELECT r FROM Receta r
           LEFT JOIN FETCH r.ingredientes ri
           LEFT JOIN FETCH ri.ingrediente
           WHERE r.id = :id
           """)
    Optional<Receta> buscarDetalleConIngredientes(@Param("id") Integer id);

    // Q8 alternativa con @EntityGraph
    @EntityGraph(attributePaths = {"fotos"})
    Optional<Receta> findWithFotosById(Integer id);

    // ---------- Q2 · JPQL con JOIN + SUM (null si la receta no tiene ingredientes) ----------
    @Query("""
           SELECT SUM(ri.cantidad * i.precio)
           FROM RecetaIngrediente ri JOIN ri.ingrediente i
           WHERE ri.receta.id = :id
           """)
    BigDecimal calcularCoste(@Param("id") Integer id);

    @Query("""
           SELECT SUM(ri.cantidad * i.kcal)
           FROM RecetaIngrediente ri JOIN ri.ingrediente i
           WHERE ri.receta.id = :id
           """)
    BigDecimal calcularKcalTotal(@Param("id") Integer id);

    // ---------- Q3 · Nativa: % de coincidencia con la despensa (RN-04) ----------
    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    @Query(value = """
           SELECT r.id                                   AS id,
                  r.nombre                               AS nombre,
                  COUNT(d.id_ingrediente)                AS disponibles,
                  COUNT(ri.id_ingrediente)               AS total,
                  ROUND(100.0 * COUNT(d.id_ingrediente) / COUNT(ri.id_ingrediente), 2) AS porcentaje
           FROM smartchef.receta r
           JOIN smartchef.receta_ingrediente ri ON ri.id_receta = r.id
           LEFT JOIN smartchef.despensa d
                  ON d.id_ingrediente = ri.id_ingrediente
                 AND d.id_usuario = :usuarioId
                 AND (d.caducidad IS NULL OR d.caducidad >= CURRENT_DATE)
           GROUP BY r.id, r.nombre
           HAVING 100.0 * COUNT(d.id_ingrediente) / COUNT(ri.id_ingrediente) > 50
           ORDER BY porcentaje DESC
           """, nativeQuery = true)
    List<RecetaCoincidenciaView> buscarPorCoincidenciaConDespensa(@Param("usuarioId") Integer usuarioId);

    // Proyección por interfaz (Q5)
    interface RecetaCoincidenciaView {
        Integer getId();
        String getNombre();
        Long getDisponibles();
        Long getTotal();
        BigDecimal getPorcentaje();
    }
}