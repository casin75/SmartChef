package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "receta_ingrediente", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaIngrediente {

    @EmbeddedId
    @Builder.Default
    private RecetaIngredienteId id = new RecetaIngredienteId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idReceta")
    @JoinColumn(name = "id_receta")
    private Receta receta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idIngrediente")
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    @Column(name = "cantidad", nullable = false, precision = 8, scale = 2)
    private BigDecimal cantidad;
}