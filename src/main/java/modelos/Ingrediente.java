package modelos;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingrediente")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "unidad_medida", nullable = false)
    private Integer unidadMedida;

    @Column(name = "kcal", precision = 8, scale = 2)
    private BigDecimal kcal;

    @Column(name = "precio", precision = 8, scale = 2)
    private BigDecimal precio;
}
