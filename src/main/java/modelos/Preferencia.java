package modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preferencia")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;
}
