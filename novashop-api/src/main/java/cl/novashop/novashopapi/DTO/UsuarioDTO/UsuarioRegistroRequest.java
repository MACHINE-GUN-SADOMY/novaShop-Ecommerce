package cl.novashop.novashopapi.DTO.UsuarioDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioRegistroRequest {
    public String nombre;
    public String email;
    public String password;
}
