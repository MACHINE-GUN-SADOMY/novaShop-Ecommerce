package cl.novashop.novashopapi.DTO.CarritoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoAgregarRequest {
    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;
}
