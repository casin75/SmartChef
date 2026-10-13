package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingrediente", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "unidad_medida", nullable = false)
    private Integer unidadMedida;

    @Column(name = "kcal", precision = 8, scale = 2)
    private BigDecimal kcal;

    @Column(name = "precio", precision = 8, scale = 2)
    private BigDecimal precio;

    @ManyToMany(mappedBy = "ingredientes", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Preferencia> preferencias = new HashSet<>();
}