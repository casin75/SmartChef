package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "receta", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "dificultad")
    private Integer dificultad;

    @Column(name = "tiempo")
    private Integer tiempo;

    @Column(name = "raciones")
    private Integer raciones;

    // List (bag) con orden por número de paso
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("numero ASC")
    @Builder.Default
    private List<Paso> pasos = new ArrayList<>();

    // Set para poder cargar pasos + ingredientes sin MultipleBagFetchException
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<RecetaIngrediente> ingredientes = new HashSet<>();

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Foto> fotos = new HashSet<>();

    @ManyToMany(mappedBy = "recetas", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Coleccion> colecciones = new HashSet<>();

    @OneToMany(mappedBy = "receta", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UsuarioReceta> historial = new HashSet<>();
}