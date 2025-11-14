package cl.novashop.novashopapi.DTO.UsuarioDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioLoginRequest {
    private String email;
    private String password;
}
