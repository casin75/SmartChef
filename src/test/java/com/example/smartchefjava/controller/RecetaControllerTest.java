package com.example.smartchefjava.controller;

import com.example.smartchefjava.exception.ConflictoException;
import com.example.smartchefjava.exception.RecursoNoEncontradoException;
import com.example.smartchefjava.service.RecetaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Solo la capa web: sin base de datos, con el servicio simulado
@WebMvcTest(RecetaController.class)
class RecetaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private RecetaService recetaService;

    @Test
    void devuelve404CuandoLaRecetaNoExiste() throws Exception {
        given(recetaService.buscarPorId(99))
                .willThrow(new RecursoNoEncontradoException("No existe la receta con id 99"));

        mockMvc.perform(get("/api/v1/recetas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("No existe la receta con id 99"));
    }

    @Test
    void devuelve400SiFaltaElNombreYNoLlamaAlServicio() throws Exception {
        String json = """
                { "dificultad": 2, "tiempo": 30, "raciones": 4 }
                """;

        mockMvc.perform(post("/api/v1/recetas").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre").value("El nombre es obligatorio"));

        verify(recetaService, never()).crear(any());
    }

    @Test
    void devuelve400SiUnIngredienteSeRepite() throws Exception {
        String json = """
                { "nombre": "Tortilla", "dificultad": 2, "tiempo": 30, "raciones": 4,
                  "ingredientes": [ { "idIngrediente": 3, "cantidad": 500 },
                                    { "idIngrediente": 3, "cantidad": 100 } ] }
                """;

        mockMvc.perform(post("/api/v1/recetas").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.ingredientes").value("La receta tiene ingredientes repetidos"));
    }

    @Test
    void devuelve400SiElIdNoEsUnNumero() throws Exception {
        mockMvc.perform(get("/api/v1/recetas/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void devuelve409AlBorrarUnaRecetaConHistorial() throws Exception {
        willThrow(new ConflictoException("No se puede eliminar «Paella»: ya aparece en el historial de algún usuario"))
                .given(recetaService).eliminar(1);

        mockMvc.perform(delete("/api/v1/recetas/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflicto con los datos existentes"));
    }

    @Test
    void devuelve204AlBorrar() throws Exception {
        mockMvc.perform(delete("/api/v1/recetas/5"))
                .andExpect(status().isNoContent());

        verify(recetaService).eliminar(5);
    }
}
