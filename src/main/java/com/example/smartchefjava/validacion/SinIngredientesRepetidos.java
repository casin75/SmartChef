package com.example.smartchefjava.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// Validador propio: una receta no puede llevar el mismo ingrediente dos veces
@Documented
@Constraint(validatedBy = SinIngredientesRepetidosValidator.class)
@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SinIngredientesRepetidos {
    String message() default "La receta tiene ingredientes repetidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
