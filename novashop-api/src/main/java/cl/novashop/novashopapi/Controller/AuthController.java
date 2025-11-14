package cl.novashop.novashopapi.Controller;

import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioLoginRequest;
import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioRegistroRequest;
import cl.novashop.novashopapi.DTO.UsuarioDTO.UsuarioRegistroResponse;
import cl.novashop.novashopapi.Model.UsuarioJpa;
import cl.novashop.novashopapi.Service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioRegistroResponse> registrar(@RequestBody UsuarioRegistroRequest request) {
        UsuarioRegistroResponse respuesta = usuarioService.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioRegistroResponse> login(@RequestBody UsuarioLoginRequest request) {
        Optional<UsuarioJpa> usuarioOpt = usuarioService.login(request);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UsuarioJpa usuarioJpa = usuarioOpt.get();
        UsuarioRegistroResponse resp = new UsuarioRegistroResponse(
                usuarioJpa.getId(),
                usuarioJpa.getNombre(),
                usuarioJpa.getEmail(),
                usuarioJpa.getFechaCre()
        );

        return ResponseEntity.ok(resp);
    }
}
