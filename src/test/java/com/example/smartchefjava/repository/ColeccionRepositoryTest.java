package com.example.smartchefjava.repository;

import com.example.smartchefjava.modelos.Coleccion;
import com.example.smartchefjava.modelos.Receta;
import com.example.smartchefjava.modelos.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ColeccionRepositoryTest {

    private final ColeccionRepository coleccionRepository;
    private final RecetaRepository recetaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntityManager em;
    private final PersistenceUnitUtil util;

    @Autowired
    ColeccionRepositoryTest(ColeccionRepository coleccionRepository,
                            RecetaRepository recetaRepository,
                            UsuarioRepository usuarioRepository,
                            EntityManager em) {
        this.coleccionRepository = coleccionRepository;
        this.recetaRepository = recetaRepository;
        this.usuarioRepository = usuarioRepository;
        this.em = em;
        this.util = em.getEntityManagerFactory().getPersistenceUnitUtil();
    }

    @Test
    @DisplayName("JOIN FETCH trae las colecciones del usuario con sus recetas")
    void joinFetchCargaLasRecetas() {
        Usuario u = guardarUsuario();
        crearColeccionConReceta(u, "TEST Favoritas");
        em.flush();
        em.clear();

        List<Coleccion> colecciones = coleccionRepository.buscarConRecetas(u.getId());

        assertThat(colecciones).hasSize(1);
        assertThat(util.isLoaded(colecciones.get(0), "recetas")).isTrue();
        assertThat(colecciones.get(0).getRecetas()).hasSize(1);
    }

    @Test
    @DisplayName("El nombre de colección se busca sin importar mayúsculas")
    void existeNombreIgnorandoMayusculas() {
        Usuario u = guardarUsuario();
        crearColeccionConReceta(u, "TEST Favoritas");
        em.flush();

        assertThat(coleccionRepository.existsByUsuarioIdAndNombreIgnoreCase(u.getId(), "test FAVORITAS")).isTrue();
        assertThat(coleccionRepository.existsByUsuarioIdAndNombreIgnoreCase(u.getId(), "otra")).isFalse();
    }

    @Test
    @DisplayName("Busca la colección por usuario y nombre")
    void buscaPorUsuarioYNombre() {
        Usuario u = guardarUsuario();
        crearColeccionConReceta(u, "TEST Para el táper");
        em.flush();

        assertThat(coleccionRepository.findByUsuarioIdAndNombreIgnoreCase(u.getId(), "test para el táper"))
                .isPresent();
    }

    // ---------- Datos de prueba ----------

    private Usuario guardarUsuario() {
        return usuarioRepository.save(Usuario.builder()
                .nombre("TEST usuario").contrasena("1234").rol(2).build());
    }

    private void crearColeccionConReceta(Usuario usuario, String nombre) {
        Receta receta = recetaRepository.save(Receta.builder()
                .nombre("TEST Receta").dificultad(1).tiempo(10).raciones(2).build());
        Coleccion coleccion = Coleccion.builder().nombre(nombre).usuario(usuario).build();
        coleccion.getRecetas().add(receta);
        coleccionRepository.save(coleccion);
    }
}
