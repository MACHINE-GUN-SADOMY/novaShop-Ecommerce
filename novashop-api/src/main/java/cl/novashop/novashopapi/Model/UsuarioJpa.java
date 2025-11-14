package cl.novashop.novashopapi.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table (name = "usuarios")
public class UsuarioJpa {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    @Column(name = "pasword")
    private String password;

    @Column(name = "fecha_cre")
    private LocalDateTime fechaCre;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;
}
