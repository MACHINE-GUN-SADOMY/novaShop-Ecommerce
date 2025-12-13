package cl.novashop.novashopapi.DTO.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioRegistroRequest {
    public String nombre;
    public String email;
    public String password;
}
