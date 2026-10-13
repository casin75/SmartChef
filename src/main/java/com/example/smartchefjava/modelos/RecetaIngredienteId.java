package com.example.smartchefjava.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecetaIngredienteId implements Serializable {

    @Column(name = "id_receta")
    private Integer idReceta;

    @Column(name = "id_ingrediente")
    private Integer idIngrediente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecetaIngredienteId that)) return false;
        return Objects.equals(idReceta, that.idReceta)
                && Objects.equals(idIngrediente, that.idIngrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReceta, idIngrediente);
    }
}
