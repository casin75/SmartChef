package com.example.smartchefjava.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificacion", schema = "smartchef")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo")
    private Integer tipo;

    @Column(name = "texto", nullable = false, columnDefinition = "TEXT")
    private String texto;
}