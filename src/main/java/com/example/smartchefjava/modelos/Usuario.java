package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @Column(name = "rol", nullable = false)
    private Integer rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Coleccion> colecciones = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Despensa> despensa = new HashSet<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UsuarioReceta> historial = new HashSet<>();

    // N:M usuario_preferencia (propietario de la relación)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_preferencia",
            schema = "smartchef",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_preferencia"))
    @Builder.Default
    private Set<Preferencia> preferencias = new HashSet<>();

    // N:M usuario_ingrediente = lista de la compra
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_ingrediente",
            schema = "smartchef",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_ingrediente"))
    @Builder.Default
    private Set<Ingrediente> listaCompra = new HashSet<>();
}
