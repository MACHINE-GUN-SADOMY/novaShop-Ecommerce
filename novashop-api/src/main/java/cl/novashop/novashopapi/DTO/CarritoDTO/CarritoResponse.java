package cl.novashop.novashopapi.DTO.CarritoDTO;

import cl.novashop.novashopapi.DTO.CarritoItemDTO.CarritoItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CarritoResponse {
    private Long carritoId;
    private Long usuarioId;
    private String estado;
    private BigDecimal total;
    private List<CarritoItemResponse> items;
}
