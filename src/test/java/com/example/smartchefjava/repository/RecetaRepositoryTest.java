package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.*;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecetaRepositoryTest {

    private final RecetaRepository recetaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final DespensaRepository despensaRepository;
    private final EntityManager em;
    private final PersistenceUnitUtil util;

    @Autowired
    RecetaRepositoryTest(RecetaRepository recetaRepository,
                         IngredienteRepository ingredienteRepository,
                         UsuarioRepository usuarioRepository,
                         DespensaRepository despensaRepository,
                         EntityManager em) {
        this.recetaRepository = recetaRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.despensaRepository = despensaRepository;
        this.em = em;
        this.util = em.getEntityManagerFactory().getPersistenceUnitUtil();
    }

    // ---------- Q1 · Derivadas ----------

    @Test
    @DisplayName("Busca por nombre ignorando mayúsculas")
    void buscaPorNombreSinImportarMayusculas() {
        crearPaella();

        List<Receta> resultado = recetaRepository.findByNombreContainingIgnoreCase("test PAELLA");

        titulo("Búsqueda por nombre: 'test PAELLA'");
        resultado.forEach(r -> dato("id=%d | %s | %d min | %d raciones",
                r.getId(), r.getNombre(), r.getTiempo(), r.getRaciones()));

        assertThat(resultado).extracting(Receta::getNombre).containsExactly("TEST Paella");
    }

    @Test
    @DisplayName("Recetas rápidas (<= 30 min) ordenadas por tiempo")
    void buscaPorTiempoMaximoOrdenado() {
        crearPaella();                              // 50 min
        guardarRecetaSimple("TEST Ensalada", 10);   // 10 min

        List<Receta> rapidas = recetaRepository.findByTiempoLessThanEqualOrderByTiempoAsc(30);

        titulo("Recetas de 30 minutos o menos (de más rápida a más lenta)");
        rapidas.forEach(r -> dato("%s -> %d min", r.getNombre(), r.getTiempo()));

        assertThat(rapidas).extracting(Receta::getNombre)
                .contains("TEST Ensalada")
                .doesNotContain("TEST Paella");
        assertThat(rapidas.stream().map(Receta::getTiempo).toList()).isSorted();
    }

    @Test
    @DisplayName("existsByNombreIgnoreCase distingue existentes de inexistentes")
    void existeNombreIgnorandoMayusculas() {
        crearPaella();

        boolean existe = recetaRepository.existsByNombreIgnoreCase("test paella");
        boolean noExiste = recetaRepository.existsByNombreIgnoreCase("TEST no existe");

        titulo("existsByNombreIgnoreCase");
        dato("'test paella' existe? -> %s", existe);
        dato("'TEST no existe' existe? -> %s", noExiste);

        assertThat(existe).isTrue();
        assertThat(noExiste).isFalse();
    }

    @Test
    @DisplayName("countByDificultad suma una más tras guardar")
    void cuentaPorDificultad() {
        long antes = recetaRepository.countByDificultad(3);

        crearPaella(); // dificultad 3

        long despues = recetaRepository.countByDificultad(3);
        titulo("countByDificultad(3)");
        dato("antes de guardar: %d", antes);
        dato("despues de guardar: %d", despues);

        assertThat(despues).isEqualTo(antes + 1);
    }

    // ---------- LAZY y JOIN FETCH ----------

    @Test
    @DisplayName("Los pasos son LAZY con findById y se cargan con JOIN FETCH")
    void joinFetchCargaLosPasos() {
        Integer id = crearPaella().receta().getId();

        em.clear();
        Receta sinFetch = recetaRepository.findById(id).orElseThrow();
        boolean cargadosSinFetch = util.isLoaded(sinFetch, "pasos");

        em.clear();
        Receta conFetch = recetaRepository.buscarDetalleConPasos(id).orElseThrow();
        boolean cargadosConFetch = util.isLoaded(conFetch, "pasos");

        titulo("LAZY vs JOIN FETCH (pasos de la receta " + id + ")");
        dato("findById          -> pasos cargados? %s", cargadosSinFetch);
        dato("buscarDetalleConPasos -> pasos cargados? %s", cargadosConFetch);
        conFetch.getPasos().forEach(p -> dato("   paso %d: %s", p.getNumero(), p.getDescripcion()));

        assertThat(cargadosSinFetch).isFalse();
        assertThat(cargadosConFetch).isTrue();
        assertThat(conFetch.getPasos()).hasSize(1);
    }

    @Test
    @DisplayName("JOIN FETCH carga ingredientes y su ingrediente en una consulta")
    void joinFetchCargaLosIngredientes() {
        Integer id = crearPaella().receta().getId();

        em.clear();
        Receta receta = recetaRepository.buscarDetalleConIngredientes(id).orElseThrow();

        titulo("Ingredientes de '" + receta.getNombre() + "' (JOIN FETCH)");
        receta.getIngredientes().forEach(ri -> dato("%s x %s (precio %s, kcal %s)",
                ri.getIngrediente().getNombre(), ri.getCantidad(),
                ri.getIngrediente().getPrecio(), ri.getIngrediente().getKcal()));

        assertThat(util.isLoaded(receta, "ingredientes")).isTrue();
        assertThat(receta.getIngredientes()).hasSize(2)
                .allSatisfy(ri -> assertThat(util.isLoaded(ri, "ingrediente")).isTrue());
    }

    // ---------- Q2 · JPQL con SUM ----------

    @Test
    @DisplayName("calcularCoste = suma de cantidad x precio")
    void calculaElCoste() {
        Integer id = crearPaella().receta().getId();

        BigDecimal coste = recetaRepository.calcularCoste(id);

        titulo("calcularCoste");
        dato("2.00 x 1.50 (arroz) + 1.00 x 5.50 (pollo) = %s", coste);

        // 2.00 x 1.50 + 1.00 x 5.50 = 8.50
        assertThat(coste).isEqualByComparingTo("8.50");
    }

    @Test
    @DisplayName("calcularCoste devuelve null si la receta no tiene ingredientes")
    void costeNullSinIngredientes() {
        Receta receta = guardarRecetaSimple("TEST Vacía", 5);

        BigDecimal coste = recetaRepository.calcularCoste(receta.getId());

        titulo("calcularCoste de una receta sin ingredientes");
        dato("coste = %s", coste);

        assertThat(coste).isNull();
    }

    // ---------- Q3 · Nativa: coincidencia con la despensa ----------

    @Test
    @DisplayName("Despensa con todos los ingredientes: 100 % de coincidencia")
    void coincidenciaCompleta() {
        Paella p = crearPaella();
        Usuario usuario = guardarUsuario();
        guardarEnDespensa(usuario, p.arroz(), LocalDate.now().plusDays(10));
        guardarEnDespensa(usuario, p.pollo(), LocalDate.now().plusDays(10));
        em.flush();

        List<RecetaRepository.RecetaCoincidenciaView> vistas =
                recetaRepository.buscarPorCoincidenciaConDespensa(usuario.getId());

        titulo("Coincidencia con la despensa (usuario con arroz y pollo, ambos en fecha)");
        vistas.forEach(v -> dato("%s -> %d de %d ingredientes (%s %%)",
                v.getNombre(), v.getDisponibles(), v.getTotal(), v.getPorcentaje()));

        Optional<RecetaRepository.RecetaCoincidenciaView> coincidencia = vistas.stream()
                .filter(v -> "TEST Paella".equals(v.getNombre()))
                .findFirst();

        assertThat(coincidencia).isPresent();
        assertThat(coincidencia.get().getDisponibles()).isEqualTo(2L);
        assertThat(coincidencia.get().getTotal()).isEqualTo(2L);
        assertThat(coincidencia.get().getPorcentaje()).isEqualByComparingTo("100.00");
    }

    @Test
    @DisplayName("Un ingrediente caducado no cuenta (1 de 2 = 50 %, no se muestra)")
    void ingredienteCaducadoNoCuenta() {
        Paella p = crearPaella();
        Usuario usuario = guardarUsuario();
        guardarEnDespensa(usuario, p.arroz(), LocalDate.now().minusDays(1)); // caducado
        guardarEnDespensa(usuario, p.pollo(), LocalDate.now().plusDays(10));
        em.flush();

        List<RecetaRepository.RecetaCoincidenciaView> vistas =
                recetaRepository.buscarPorCoincidenciaConDespensa(usuario.getId());

        titulo("Coincidencia con el arroz caducado ayer y el pollo en fecha");
        dato("recetas propuestas: %d (la paella queda en 1 de 2 = 50 %%, no pasa el corte)", vistas.size());
        vistas.forEach(v -> dato("%s -> %s %%", v.getNombre(), v.getPorcentaje()));

        boolean aparece = vistas.stream().anyMatch(v -> "TEST Paella".equals(v.getNombre()));

        assertThat(aparece).isFalse(); // exige MÁS del 50 % (RN-04)
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

    private record Paella(Receta receta, Ingrediente arroz, Ingrediente pollo) {
    }

    private Paella crearPaella() {
        Ingrediente arroz = guardarIngrediente("TEST Arroz", "360.00", "1.50");
        Ingrediente pollo = guardarIngrediente("TEST Pollo", "165.00", "5.50");

        Receta receta = Receta.builder()
                .nombre("TEST Paella").descripcion("Receta de prueba")
                .dificultad(3).tiempo(50).raciones(4).build();
        receta.getPasos().add(Paso.builder().numero(1).descripcion("Cortar el pollo").receta(receta).build());
        receta.getIngredientes().add(RecetaIngrediente.builder()
                .receta(receta).ingrediente(arroz).cantidad(new BigDecimal("2.00")).build());
        receta.getIngredientes().add(RecetaIngrediente.builder()
                .receta(receta).ingrediente(pollo).cantidad(new BigDecimal("1.00")).build());

        recetaRepository.save(receta);
        em.flush();
        return new Paella(receta, arroz, pollo);
    }

    private Receta guardarRecetaSimple(String nombre, int tiempo) {
        return recetaRepository.saveAndFlush(Receta.builder()
                .nombre(nombre).dificultad(1).tiempo(tiempo).raciones(2).build());
    }

    private Ingrediente guardarIngrediente(String nombre, String kcal, String precio) {
        return ingredienteRepository.save(Ingrediente.builder()
                .nombre(nombre).unidadMedida(1)
                .kcal(new BigDecimal(kcal)).precio(new BigDecimal(precio)).build());
    }

    private Usuario guardarUsuario() {
        return usuarioRepository.save(Usuario.builder()
                .nombre("TEST usuario").contrasena("1234").rol(2).build());
    }

    private void guardarEnDespensa(Usuario usuario, Ingrediente ingrediente, LocalDate caducidad) {
        despensaRepository.save(Despensa.builder()
                .id(new DespensaId(usuario.getId(), ingrediente.getId()))
                .usuario(usuario).ingrediente(ingrediente)
                .cantidad(new BigDecimal("1.00")).caducidad(caducidad).build());
    }
}
