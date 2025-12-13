package cl.novashop.novashopapi.Service;

import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioLoginRequest;
import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioLoginResponse;
import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioResponse;
import cl.novashop.novashopapi.Model.RolUsuario;
import cl.novashop.novashopapi.Model.UsuarioJpa;
import cl.novashop.novashopapi.Repository.UsuarioJpaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.*;

import java.util.Optional;

@Service @RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioJpaRepository usuarioJpaRepository;

    public void validarAdmin(Long userId) {
        UsuarioResponse usuario = this.buscarUsuarioPorId(userId);
        if (!usuario.getRol().equals(RolUsuario.ADMIN)) {
            throw new RuntimeException("Acceso denegado. Solo ADMIN.");
        }
    }

    public UsuarioLoginResponse login(UsuarioLoginRequest request) {

        UsuarioJpa usuario = usuarioJpaRepository
                .findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        UsuarioResponse dto = new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol()
        );

        return new UsuarioLoginResponse("Login exitoso", dto);
    }

    public UsuarioResponse registrarUsuario(UsuarioJpa usuario) {
        if (usuario.getRol() == null) {
            usuario.setRol(RolUsuario.CUSTOMER);
        }

        UsuarioJpa guardado = usuarioJpaRepository.save(usuario);

        return new UsuarioResponse(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getEmail(),
                guardado.getRol()
        );
    }

    public UsuarioResponse buscarUsuarioPorId(Long id) {

        UsuarioJpa usuario = usuarioJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }
}
