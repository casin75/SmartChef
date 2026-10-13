package modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fotos")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "url", nullable = false)
    private String url;
}