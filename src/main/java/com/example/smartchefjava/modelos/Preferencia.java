package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "preferencia", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;

    // N:M preferencia_ingrediente (propietario)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "preferencia_ingrediente",
            schema = "smartchef",
            joinColumns = @JoinColumn(name = "id_preferencia"),
            inverseJoinColumns = @JoinColumn(name = "id_ingrediente"))
    @Builder.Default
    private Set<Ingrediente> ingredientes = new HashSet<>();

    @ManyToMany(mappedBy = "preferencias", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Usuario> usuarios = new HashSet<>();
}