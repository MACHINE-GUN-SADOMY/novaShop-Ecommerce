package cl.novashop.novashopapi.Service;

import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioLoginRequest;
import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioRegistroRequest;
import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioRegistroResponse;
import cl.novashop.novashopapi.Model.UsuarioJpa;
import cl.novashop.novashopapi.Repository.UsuarioJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.*;

import java.time     .LocalDateTime;
import java.util.Optional;

@Service @AllArgsConstructor
public class UsuarioService {
    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRegistroResponse registrarUsuario(UsuarioRegistroRequest usuarioRegistroRequest) {
        UsuarioJpa usuario = new UsuarioJpa();
        usuario.setNombre(usuarioRegistroRequest.getNombre());
        usuario.setEmail(usuarioRegistroRequest.getEmail());
        usuario.setPassword(usuarioRegistroRequest.getPassword());
        usuario.setFechaCre(LocalDateTime.now());

        UsuarioJpa usuarioGuardado = usuarioJpaRepository.save(usuario);

        return new UsuarioRegistroResponse(
                usuarioGuardado.getId(),
                usuarioGuardado.getNombre(),
                usuarioGuardado.getEmail(),
                usuarioGuardado.getFechaCre()
        );
    }

    public Optional<UsuarioJpa> login(UsuarioLoginRequest usuarioLoginRequest) {
        return usuarioJpaRepository.login(usuarioLoginRequest.getEmail(), usuarioLoginRequest.getPassword());
    }
}
