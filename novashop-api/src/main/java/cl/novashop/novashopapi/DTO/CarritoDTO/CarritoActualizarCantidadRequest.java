package cl.novashop.novashopapi.DTO.CarritoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class CarritoActualizarCantidadRequest {
    private Integer cantidad;
}
