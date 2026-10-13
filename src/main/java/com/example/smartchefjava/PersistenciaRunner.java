package com.example.smartchefjava;

import com.example.smartchefjava.modelos.Receta;
import com.example.smartchefjava.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.verificar-persistencia", havingValue = "true")
public class PersistenciaRunner implements CommandLineRunner {

    private final RecetaRepository recetaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final DespensaRepository despensaRepository;
    private final ColeccionRepository coleccionRepository;
    private final UsuarioRecetaRepository usuarioRecetaRepository;
    private final PlatformTransactionManager txManager;

    @Override
    public void run(String... args) {
        log.info("===== VERIFICACIÓN DE PERSISTENCIA =====");

        // 1. Mapeo básico (si el esquema no coincide, ya habría fallado al arrancar)
        log.info("Recetas: {} | Ingredientes: {} | Usuarios: {}",
                recetaRepository.count(), ingredienteRepository.count(), usuarioRepository.count());

        // 2. Derivadas
        log.info("Recetas que contienen 'pa': {}",
                recetaRepository.findByNombreContainingIgnoreCase("pa").size());
        log.info("Recetas de <= 30 min: {}",
                recetaRepository.findByTiempoLessThanEqualOrderByTiempoAsc(30).size());

        // 3. LAZY: fuera de transacción debe lanzar LazyInitializationException
        Receta r = recetaRepository.findById(1).orElseThrow();
        try {
            r.getPasos().size();
            log.error("FALLO: pasos no era LAZY");
        } catch (LazyInitializationException e) {
            log.info("OK: pasos es LAZY (LazyInitializationException esperada)");
        }

        // 4. JOIN FETCH: ahora sí se puede acceder fuera de transacción
        Receta detalle = recetaRepository.buscarDetalleConPasos(1).orElseThrow();
        log.info("OK JOIN FETCH: receta '{}' con {} pasos", detalle.getNombre(), detalle.getPasos().size());

        Receta conIng = recetaRepository.buscarDetalleConIngredientes(1).orElseThrow();
        conIng.getIngredientes().forEach(ri ->
                log.info("   - {} x {}", ri.getIngrediente().getNombre(), ri.getCantidad()));

        // 5. JPQL con SUM
        log.info("Coste total receta 1: {}", recetaRepository.calcularCoste(1));

        // 6. Nativa: coincidencia con la despensa del usuario 1
        recetaRepository.buscarPorCoincidenciaConDespensa(1).forEach(v ->
                log.info("Coincidencia: {} -> {}/{} ({}%)",
                        v.getNombre(), v.getDisponibles(), v.getTotal(), v.getPorcentaje()));

        // 7. Clave compuesta (despensa) + JOIN FETCH
        despensaRepository.buscarDespensaConIngredientes(1).forEach(d ->
                log.info("Despensa u1: {} x {} (caduca {})",
                        d.getIngrediente().getNombre(), d.getCantidad(), d.getCaducidad()));

        // 8. ManyToMany con JOIN FETCH
        coleccionRepository.buscarConRecetas(1).forEach(c ->
                log.info("Colección '{}' con {} recetas", c.getNombre(), c.getRecetas().size()));

        // 9. Informes (proyecciones + DATE_TRUNC)
        usuarioRecetaRepository.recetasMasRegistradas().forEach(v ->
                log.info("Popular: {} ({} veces)", v.getNombre(), v.getVeces()));
        usuarioRecetaRepository.informeMensual(
                LocalDateTime.of(2026, 1, 1, 0, 0), LocalDateTime.of(2026, 12, 31, 23, 59)
        ).forEach(v -> log.info("Informe {} | {} | {}", v.getMes(), v.getReceta(), v.getVeces()));

        // 10. @Modifying: se ejecuta y se hace ROLLBACK para no tocar tus datos
        new TransactionTemplate(txManager).executeWithoutResult(status -> {
            int borrados = despensaRepository.eliminarCaducadosAntesDe(LocalDate.now().minusDays(7));
            log.info("Caducados borrados (se revierte): {}", borrados);
            status.setRollbackOnly();
        });

        log.info("===== FIN DE LA VERIFICACIÓN =====");
    }
}