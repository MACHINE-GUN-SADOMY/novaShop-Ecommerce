package cl.novashop.novashopapi.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table (name = "pedidos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PedidoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "estado")
    private String estado = "PENDIENTE";

    @Column(name = "direccion_envio", length = 255)
    private String direccionEnvio;

    @Column(name = "creado_en", updatable = false, insertable = false)
    private LocalDateTime creadoEn;

    @Column(name = "carrito_id")
    private Long carritoId;
}
