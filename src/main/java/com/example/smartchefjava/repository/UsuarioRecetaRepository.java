package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.UsuarioReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioRecetaRepository extends JpaRepository<UsuarioReceta, Integer> {

    // Q1
    List<UsuarioReceta> findByUsuarioIdAndFechaBetweenOrderByFechaAsc(Integer usuarioId,
                                                                      LocalDateTime desde,
                                                                      LocalDateTime hasta);

    boolean existsByUsuarioIdAndRecetaId(Integer usuarioId, Integer recetaId);

    // JOIN FETCH para el historial (sin N+1)
    @Query("""
           SELECT ur FROM UsuarioReceta ur
           JOIN FETCH ur.receta
           WHERE ur.usuario.id = :usuarioId
           ORDER BY ur.fecha DESC
           """)
    List<UsuarioReceta> buscarHistorialConReceta(@Param("usuarioId") Integer usuarioId);

    // Q2 · JPQL con COUNT + GROUP BY + proyección por interfaz
    @Query("""
           SELECT r.id AS recetaId, r.nombre AS nombre, COUNT(ur) AS veces
           FROM UsuarioReceta ur JOIN ur.receta r
           GROUP BY r.id, r.nombre
           ORDER BY COUNT(ur) DESC
           """)
    List<RecetaPopularView> recetasMasRegistradas();

    // Q3 · Nativa con DATE_TRUNC / TO_CHAR para informe mensual
    @Query(value = """
           SELECT TO_CHAR(DATE_TRUNC('month', ur.fecha), 'YYYY-MM') AS mes,
                  r.nombre                                           AS receta,
                  COUNT(*)                                           AS veces
           FROM smartchef.usuario_receta ur
           JOIN smartchef.receta r ON r.id = ur.id_receta
           WHERE ur.fecha BETWEEN :desde AND :hasta
           GROUP BY DATE_TRUNC('month', ur.fecha), r.nombre
           ORDER BY mes, veces DESC
           """, nativeQuery = true)
    List<InformeMensualView> informeMensual(@Param("desde") LocalDateTime desde,
                                            @Param("hasta") LocalDateTime hasta);

    interface RecetaPopularView {
        Integer getRecetaId();
        String getNombre();
        Long getVeces();
    }

    interface InformeMensualView {
        String getMes();
        String getReceta();
        Long getVeces();
    }
}