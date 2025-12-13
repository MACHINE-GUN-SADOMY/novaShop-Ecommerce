package cl.novashop.novashopapi.DTO.PedidoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PedidoRequest {
    private Long usuarioId;
    private Long carritoId;
    private String direccionEnvio;
    private BigDecimal total;
    private List<PedidoItemsRequest> items;
}
