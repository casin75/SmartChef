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
public class DespensaId implements Serializable {

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_ingrediente")
    private Integer idIngrediente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DespensaId that)) return false;
        return Objects.equals(idUsuario, that.idUsuario)
                && Objects.equals(idIngrediente, that.idIngrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idIngrediente);
    }
}