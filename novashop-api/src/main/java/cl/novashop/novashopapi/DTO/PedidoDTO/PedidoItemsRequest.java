package cl.novashop.novashopapi.DTO.PedidoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemsRequest {
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
