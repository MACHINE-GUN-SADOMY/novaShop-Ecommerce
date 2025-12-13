package cl.novashop.novashopapi.DTO.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Getter
@Setter
public class UsuarioRegistroResponse {
    public String email;
    public String password;
}
