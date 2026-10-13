package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coleccion", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // N:M coleccion_receta (propietario)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "coleccion_receta",
            schema = "smartchef",
            joinColumns = @JoinColumn(name = "id_coleccion"),
            inverseJoinColumns = @JoinColumn(name = "id_receta"))
    @Builder.Default
    private Set<Receta> recetas = new HashSet<>();
}