package modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @Column(name = "rol", nullable = false)
    private Integer rol;
}
