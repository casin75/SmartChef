package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Despensa;
import com.example.smartchefjava.modelos.DespensaId;
import com.example.smartchefjava.modelos.Ingrediente;
import com.example.smartchefjava.modelos.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DespensaRepositoryTest {

    private final DespensaRepository despensaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntityManager em;
    private final PersistenceUnitUtil util;

    @Autowired
    DespensaRepositoryTest(DespensaRepository despensaRepository,
                           IngredienteRepository ingredienteRepository,
                           UsuarioRepository usuarioRepository,
                           EntityManager em) {
        this.despensaRepository = despensaRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.em = em;
        this.util = em.getEntityManagerFactory().getPersistenceUnitUtil();
    }

    @Test
    @DisplayName("La despensa sale ordenada: primero lo que antes caduca")
    void ordenaPorCaducidad() {
        Usuario u = guardarUsuario();
        Ingrediente leche = guardarIngrediente("TEST Leche");
        Ingrediente arroz = guardarIngrediente("TEST Arroz");
        guardarEnDespensa(u, arroz, LocalDate.now().plusDays(20));
        guardarEnDespensa(u, leche, LocalDate.now().plusDays(5));
        em.flush();

        List<Despensa> despensa = despensaRepository.findByUsuarioIdOrderByCaducidadAsc(u.getId());

        titulo("Despensa ordenada por caducidad");
        despensa.forEach(d -> dato("%s x %s -> caduca %s",
                d.getIngrediente().getNombre(), d.getCantidad(), d.getCaducidad()));

        assertThat(despensa).extracting(d -> d.getIngrediente().getNombre())
                .containsExactly("TEST Leche", "TEST Arroz");
    }

    @Test
    @DisplayName("La clave compuesta (usuario + ingrediente) permite buscar por id")
    void buscaPorClaveCompuesta() {
        Usuario u = guardarUsuario();
        Ingrediente arroz = guardarIngrediente("TEST Arroz");
        guardarEnDespensa(u, arroz, LocalDate.now().plusDays(10));
        em.flush();
        em.clear();

        var encontrada = despensaRepository.findById(new DespensaId(u.getId(), arroz.getId()));

        titulo("Búsqueda por clave compuesta (usuario " + u.getId() + ", ingrediente " + arroz.getId() + ")");
        dato("encontrada? %s", encontrada.isPresent());
        encontrada.ifPresent(d -> dato("cantidad=%s | caducidad=%s", d.getCantidad(), d.getCaducidad()));

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getCantidad()).isEqualByComparingTo("1.00");
    }

    @Test
    @DisplayName("JOIN FETCH trae el ingrediente cargado; la derivada no")
    void joinFetchTraeElIngrediente() {
        Usuario u = guardarUsuario();
        guardarEnDespensa(u, guardarIngrediente("TEST Arroz"), LocalDate.now().plusDays(10));
        em.flush();

        em.clear();
        Despensa sinFetch = despensaRepository.findByUsuarioIdOrderByCaducidadAsc(u.getId()).get(0);
        boolean cargadoSinFetch = util.isLoaded(sinFetch, "ingrediente");

        em.clear();
        Despensa conFetch = despensaRepository.buscarDespensaConIngredientes(u.getId()).get(0);
        boolean cargadoConFetch = util.isLoaded(conFetch, "ingrediente");

        titulo("LAZY vs JOIN FETCH (ingrediente de la despensa)");
        dato("consulta derivada       -> ingrediente cargado? %s", cargadoSinFetch);
        dato("buscarDespensaConIngredientes -> ingrediente cargado? %s (%s)",
                cargadoConFetch, conFetch.getIngrediente().getNombre());

        assertThat(cargadoSinFetch).isFalse();
        assertThat(cargadoConFetch).isTrue();
    }

    @Test
    @DisplayName("Próximos a caducar en una ventana de fechas")
    void buscaProximosACaducar() {
        Usuario u = guardarUsuario();
        guardarEnDespensa(u, guardarIngrediente("TEST Yogur"), LocalDate.now().plusDays(1));
        guardarEnDespensa(u, guardarIngrediente("TEST Arroz"), LocalDate.now().plusDays(30));
        em.flush();

        LocalDate desde = LocalDate.now();
        LocalDate hasta = LocalDate.now().plusDays(2);
        List<Despensa> proximos = despensaRepository.buscarProximosACaducar(desde, hasta);

        titulo("Próximos a caducar entre " + desde + " y " + hasta + " (todos los usuarios)");
        proximos.forEach(d -> dato("%s de %s -> caduca %s",
                d.getIngrediente().getNombre(), d.getUsuario().getNombre(), d.getCaducidad()));

        assertThat(proximos)
                .filteredOn(d -> d.getUsuario().getId().equals(u.getId()))
                .extracting(d -> d.getIngrediente().getNombre())
                .containsExactly("TEST Yogur");
    }

    @Test
    @DisplayName("@Modifying: borra los caducados hace más de 7 días y respeta el resto")
    void eliminaSoloLosCaducados() {
        Usuario u = guardarUsuario();
        Integer idUsuario = u.getId();
        guardarEnDespensa(u, guardarIngrediente("TEST Viejo"), LocalDate.now().minusDays(30));
        guardarEnDespensa(u, guardarIngrediente("TEST Fresco"), LocalDate.now().plusDays(10));
        em.flush();

        LocalDate limite = LocalDate.now().minusDays(7);
        int borrados = despensaRepository.eliminarCaducadosAntesDe(limite);
        List<Despensa> quedan = despensaRepository.findByUsuarioIdOrderByCaducidadAsc(idUsuario);

        titulo("DELETE masivo de caducados antes de " + limite + " (se revierte al terminar)");
        dato("filas borradas en total: %d", borrados);
        quedan.forEach(d -> dato("le queda al usuario de prueba: %s (caduca %s)",
                d.getIngrediente().getNombre(), d.getCaducidad()));

        assertThat(borrados).isGreaterThanOrEqualTo(1);
        assertThat(quedan).extracting(d -> d.getIngrediente().getNombre())
                .containsExactly("TEST Fresco");
    }

    // ---------- Salida por consola ----------

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }

    private static void dato(String formato, Object... args) {
        System.out.println("   " + String.format(formato, args));
    }

    // ---------- Datos de prueba ----------

    private Usuario guardarUsuario() {
        return usuarioRepository.save(Usuario.builder()
                .nombre("TEST usuario").contrasena("1234").rol(2).build());
    }

    private Ingrediente guardarIngrediente(String nombre) {
        return ingredienteRepository.save(Ingrediente.builder()
                .nombre(nombre).unidadMedida(1)
                .kcal(new BigDecimal("100.00")).precio(new BigDecimal("1.00")).build());
    }

    private void guardarEnDespensa(Usuario usuario, Ingrediente ingrediente, LocalDate caducidad) {
        despensaRepository.save(Despensa.builder()
                .id(new DespensaId(usuario.getId(), ingrediente.getId()))
                .usuario(usuario).ingrediente(ingrediente)
                .cantidad(new BigDecimal("1.00")).caducidad(caducidad).build());
    }
}
