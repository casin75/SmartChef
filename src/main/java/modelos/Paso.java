package modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pasos")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Paso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}