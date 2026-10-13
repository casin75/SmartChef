package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fotos", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;
}