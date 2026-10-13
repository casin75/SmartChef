package modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "receta")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "dificultad")
    private Integer dificultad;

    @Column(name = "tiempo")
    private Integer tiempo;

    @Column(name = "raciones")
    private Integer raciones;
}
