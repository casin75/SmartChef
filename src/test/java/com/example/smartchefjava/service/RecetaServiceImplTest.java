package com.example.smartchefjava.service;

import com.example.smartchefjava.dto.request.IngredienteRecetaRequest;
import com.example.smartchefjava.dto.request.RecetaRequest;
import com.example.smartchefjava.exception.ConflictoException;
import com.example.smartchefjava.exception.RecursoNoEncontradoException;
import com.example.smartchefjava.mapper.RecetaMapper;
import com.example.smartchefjava.modelos.Receta;
import com.example.smartchefjava.repository.IngredienteRepository;
import com.example.smartchefjava.repository.RecetaRepository;
import com.example.smartchefjava.repository.UsuarioRecetaRepository;
import com.example.smartchefjava.service.impl.RecetaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

// Las reglas de negocio, sin servidor ni base de datos: los repositorios son simuladores
@ExtendWith(MockitoExtension.class)
class RecetaServiceImplTest {

    @Mock private RecetaRepository recetaRepository;
    @Mock private IngredienteRepository ingredienteRepository;
    @Mock private UsuarioRecetaRepository usuarioRecetaRepository;
    @Mock private RecetaMapper recetaMapper;

    @InjectMocks private RecetaServiceImpl recetaService;

    private RecetaRequest peticion(String nombre, List<IngredienteRecetaRequest> ingredientes) {
        return new RecetaRequest(nombre, null, 2, 30, 4, List.of("Paso 1"), ingredientes, null);
    }

    @Test
    void noCreaUnaRecetaConUnNombreQueYaExiste() {
        given(recetaRepository.existsByNombreIgnoreCase("Tortilla de patatas")).willReturn(true);

        assertThatThrownBy(() -> recetaService.crear(peticion("Tortilla de patatas", null)))
                .isInstanceOf(ConflictoException.class)
                .hasMessageContaining("Ya existe una receta llamada «Tortilla de patatas»");

        then(recetaRepository).should(never()).save(any());
    }

    @Test
    void noCreaUnaRecetaConIngredientesQueNoExisten() {
        given(recetaRepository.existsByNombreIgnoreCase("Gazpacho")).willReturn(false);
        given(ingredienteRepository.findAllById(any())).willReturn(List.of());

        var ingredientes = List.of(new IngredienteRecetaRequest(999, new BigDecimal("100")));

        assertThatThrownBy(() -> recetaService.crear(peticion("Gazpacho", ingredientes)))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("[999]");

        then(recetaRepository).should(never()).save(any());
    }

    @Test
    void noBorraUnaRecetaQueEstaEnElHistorial() {
        Receta paella = Receta.builder().id(1).nombre("Paella de pollo").build();
        given(recetaRepository.findById(1)).willReturn(Optional.of(paella));
        given(usuarioRecetaRepository.existsByRecetaId(1)).willReturn(true);

        assertThatThrownBy(() -> recetaService.eliminar(1))
                .isInstanceOf(ConflictoException.class)
                .hasMessageContaining("historial");

        then(recetaRepository).should(never()).delete(any());
    }
}
