package com.example.smartchefjava.validacion;

import com.example.smartchefjava.dto.request.IngredienteRecetaRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SinIngredientesRepetidosValidator
        implements ConstraintValidator<SinIngredientesRepetidos, List<IngredienteRecetaRequest>> {

    @Override
    public boolean isValid(List<IngredienteRecetaRequest> ingredientes, ConstraintValidatorContext contexto) {
        if (ingredientes == null) return true;   // de los nulos se encarga @NotNull si hiciera falta
        Set<Integer> vistos = new HashSet<>();
        return ingredientes.stream()
                .map(IngredienteRecetaRequest::idIngrediente)
                .filter(Objects::nonNull)
                .allMatch(vistos::add);          // add devuelve false si ya estaba
    }
}
