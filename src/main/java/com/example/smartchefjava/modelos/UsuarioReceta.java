package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_receta", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "tipo")
    private Integer tipo;

    // Auditoría simple: rellena la fecha si no viene informada
    @PrePersist
    void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}