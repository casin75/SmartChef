package com.example.smartchefjava.service.impl;

import com.example.smartchefjava.dto.request.IngredienteRecetaRequest;
import com.example.smartchefjava.dto.request.RecetaRequest;
import com.example.smartchefjava.dto.response.PaginaResponse;
import com.example.smartchefjava.dto.response.RecetaDetalleResponse;
import com.example.smartchefjava.dto.response.RecetaResumenResponse;
import com.example.smartchefjava.exception.ConflictoException;
import com.example.smartchefjava.exception.RecursoNoEncontradoException;
import com.example.smartchefjava.mapper.RecetaMapper;
import com.example.smartchefjava.modelos.*;
import com.example.smartchefjava.repository.IngredienteRepository;
import com.example.smartchefjava.repository.RecetaRepository;
import com.example.smartchefjava.repository.UsuarioRecetaRepository;
import com.example.smartchefjava.service.RecetaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)   // por defecto todo es lectura; los métodos que escriben lo sobrescriben
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final UsuarioRecetaRepository usuarioRecetaRepository;
    private final RecetaMapper recetaMapper;

    @Override
    public PaginaResponse<RecetaResumenResponse> listar(String nombre, Pageable pageable) {
        Page<Receta> recetas = (nombre == null || nombre.isBlank())
                ? recetaRepository.findAll(pageable)
                : recetaRepository.findByNombreContainingIgnoreCase(nombre.trim(), pageable);

        return PaginaResponse.de(recetas.map(recetaMapper::aResumen));
    }

    @Override
    public RecetaDetalleResponse buscarPorId(Integer id) {
        return recetaMapper.aDetalle(cargarDetalle(id));
    }

    @Override
    @Transactional
    public RecetaDetalleResponse crear(RecetaRequest peticion) {
        // 1. Reglas ANTES de tocar nada
        if (recetaRepository.existsByNombreIgnoreCase(peticion.nombre().trim())) {
            throw new ConflictoException("Ya existe una receta llamada «" + peticion.nombre().trim() + "»");
        }
        Map<Integer, Ingrediente> ingredientes = buscarIngredientes(peticion.ingredientes());

        // 2. Construir la entidad: lo que el mapper ignoró lo ponemos aquí
        Receta receta = recetaMapper.aEntidad(peticion);
        receta.setNombre(peticion.nombre().trim());
        ponerPasos(receta, peticion.pasos());
        ponerIngredientes(receta, peticion.ingredientes(), ingredientes);
        ponerFotos(receta, peticion.fotos());

        // 3. Guardar y devolver
        Receta guardada = recetaRepository.save(receta);
        log.info("Receta creada: id={} nombre={}", guardada.getId(), guardada.getNombre());
        return recetaMapper.aDetalle(guardada);
    }

    @Override
    @Transactional
    public RecetaDetalleResponse actualizar(Integer id, RecetaRequest peticion) {
        Receta receta = cargarDetalle(id);

        // El nombre puede repetirse solo si es el de la propia receta
        if (recetaRepository.existsByNombreIgnoreCaseAndIdNot(peticion.nombre().trim(), id)) {
            throw new ConflictoException("Ya existe otra receta llamada «" + peticion.nombre().trim() + "»");
        }
        Map<Integer, Ingrediente> ingredientes = buscarIngredientes(peticion.ingredientes());

        recetaMapper.actualizar(receta, peticion);      // modifica la entidad en sitio
        receta.setNombre(peticion.nombre().trim());
        if (peticion.pasos() != null) {
            receta.getPasos().clear();                   // orphanRemoval borra los antiguos
            ponerPasos(receta, peticion.pasos());
        }
        if (peticion.ingredientes() != null) {
            sincronizarIngredientes(receta, peticion.ingredientes(), ingredientes);
        }
        if (peticion.fotos() != null) {
            receta.getFotos().clear();
            ponerFotos(receta, peticion.fotos());
        }

        log.info("Receta actualizada: id={}", id);
        return recetaMapper.aDetalle(receta);           // sin save(): la entidad está gestionada
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la receta con id " + id));

        // Si ya está en el historial de alguien, borrarla le dejaría el historial a medias
        if (usuarioRecetaRepository.existsByRecetaId(id)) {
            throw new ConflictoException("No se puede eliminar «" + receta.getNombre()
                    + "»: ya aparece en el historial de algún usuario");
        }

        recetaRepository.delete(receta);
        log.info("Receta eliminada: id={}", id);
    }

    // ---------- Ayudantes privados ----------

    // Dos consultas con JOIN FETCH (pasos e ingredientes) en la misma transacción: sin N+1
    private Receta cargarDetalle(Integer id) {
        Receta receta = recetaRepository.buscarDetalleConPasos(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la receta con id " + id));
        recetaRepository.buscarDetalleConIngredientes(id);
        return receta;
    }

    // Comprueba de una vez que existen todos los ingredientes pedidos
    private Map<Integer, Ingrediente> buscarIngredientes(List<IngredienteRecetaRequest> pedidos) {
        if (pedidos == null || pedidos.isEmpty()) return Map.of();

        Set<Integer> ids = pedidos.stream().map(IngredienteRecetaRequest::idIngrediente).collect(Collectors.toSet());
        Map<Integer, Ingrediente> encontrados = ingredienteRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Ingrediente::getId, Function.identity()));

        List<Integer> faltan = ids.stream().filter(i -> !encontrados.containsKey(i)).sorted().toList();
        if (!faltan.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen los ingredientes con id " + faltan);
        }
        return encontrados;
    }

    private void ponerPasos(Receta receta, List<String> pasos) {
        if (pasos == null) return;
        int numero = 1;
        for (String texto : pasos) {
            receta.getPasos().add(Paso.builder()
                    .numero(numero++)
                    .descripcion(texto.trim())
                    .receta(receta)
                    .build());
        }
    }

    private void ponerIngredientes(Receta receta, List<IngredienteRecetaRequest> pedidos,
                                   Map<Integer, Ingrediente> ingredientes) {
        if (pedidos == null) return;
        for (IngredienteRecetaRequest pedido : pedidos) {
            receta.getIngredientes().add(RecetaIngrediente.builder()
                    .receta(receta)
                    .ingrediente(ingredientes.get(pedido.idIngrediente()))
                    .cantidad(pedido.cantidad())
                    .build());
        }
    }

    // Al editar no se puede borrar y volver a crear la misma fila (misma clave compuesta):
    // se actualiza la cantidad de los que siguen, se quitan los que sobran y se añaden los nuevos
    private void sincronizarIngredientes(Receta receta, List<IngredienteRecetaRequest> pedidos,
                                         Map<Integer, Ingrediente> ingredientes) {
        Map<Integer, IngredienteRecetaRequest> porId = pedidos.stream()
                .collect(Collectors.toMap(IngredienteRecetaRequest::idIngrediente, Function.identity()));

        receta.getIngredientes().removeIf(ri -> !porId.containsKey(ri.getIngrediente().getId()));
        receta.getIngredientes().forEach(ri ->
                ri.setCantidad(porId.remove(ri.getIngrediente().getId()).cantidad()));
        ponerIngredientes(receta, new ArrayList<>(porId.values()), ingredientes);
    }

    private void ponerFotos(Receta receta, List<String> fotos) {
        if (fotos == null) return;
        fotos.forEach(url -> receta.getFotos().add(Foto.builder().url(url.trim()).receta(receta).build()));
    }
}
