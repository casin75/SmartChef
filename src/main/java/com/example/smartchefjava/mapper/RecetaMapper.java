package com.example.smartchefjava.mapper;

import com.example.smartchefjava.dto.request.RecetaRequest;
import com.example.smartchefjava.dto.response.IngredienteRecetaResponse;
import com.example.smartchefjava.dto.response.RecetaDetalleResponse;
import com.example.smartchefjava.dto.response.RecetaResumenResponse;
import com.example.smartchefjava.modelos.Foto;
import com.example.smartchefjava.modelos.Receta;
import com.example.smartchefjava.modelos.RecetaIngrediente;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecetaMapper {

    // ---------- Entidad → DTO ----------

    @Mapping(target = "foto", expression = "java(primeraFoto(receta.getFotos()))")   // calculado
    RecetaResumenResponse aResumen(Receta receta);

    RecetaDetalleResponse aDetalle(Receta receta);

    // Aplanar: del ingrediente solo queremos su id, su nombre y su unidad
    @Mapping(target = "idIngrediente", source = "ingrediente.id")
    @Mapping(target = "nombre", source = "ingrediente.nombre")
    @Mapping(target = "unidadMedida", source = "ingrediente.unidadMedida")
    IngredienteRecetaResponse aIngredienteResponse(RecetaIngrediente recetaIngrediente);

    // Los ingredientes son un Set: los devolvemos ordenados por nombre
    default List<IngredienteRecetaResponse> aIngredientesResponse(Set<RecetaIngrediente> ingredientes) {
        return ingredientes.stream()
                .map(this::aIngredienteResponse)
                .sorted(Comparator.comparing(IngredienteRecetaResponse::nombre))
                .toList();
    }

    // De cada foto solo nos interesa la dirección
    default String aUrl(Foto foto) {
        return foto.getUrl();
    }

    default String primeraFoto(Set<Foto> fotos) {
        return fotos.stream()
                .min(Comparator.comparing(Foto::getId))
                .map(Foto::getUrl)
                .orElse(null);
    }

    // ---------- DTO → Entidad ----------

    // Lo que no se copia lo rellena otro: el id la base de datos, y los hijos el servicio
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pasos", ignore = true)
    @Mapping(target = "ingredientes", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "colecciones", ignore = true)
    @Mapping(target = "historial", ignore = true)
    Receta aEntidad(RecetaRequest peticion);

    // PUT: modifica la receta que ya existe en lugar de crear otra
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pasos", ignore = true)
    @Mapping(target = "ingredientes", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "colecciones", ignore = true)
    @Mapping(target = "historial", ignore = true)
    void actualizar(@MappingTarget Receta destino, RecetaRequest peticion);
}
