package cl.novashop.novashopapi.DTO.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioLoginResponse {
    private String message;
    private UsuarioResponse user;
}
