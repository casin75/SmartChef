package modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificacion")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="tipo")
    private Integer tipo;

    @Column(name="texto")
    private String texto;
}
