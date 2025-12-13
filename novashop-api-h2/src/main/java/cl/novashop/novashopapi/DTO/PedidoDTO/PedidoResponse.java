package cl.novashop.novashopapi.DTO.PedidoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PedidoResponse {
    private Long id;
    private Long usuarioId;
    private BigDecimal total;
    private String estado;
    private String direccionEnvio;
    private LocalDateTime creadoEn;
    private Long carritoId;
    private List<PedidoItemsResponse> items;
}
