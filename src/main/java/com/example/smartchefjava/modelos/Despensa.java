package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "despensa", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Despensa {

    @EmbeddedId
    @Builder.Default
    private DespensaId id = new DespensaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idIngrediente")
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    @Column(name = "cantidad", nullable = false, precision = 8, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "caducidad")
    private LocalDate caducidad;
}