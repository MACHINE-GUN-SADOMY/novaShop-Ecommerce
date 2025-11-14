package cl.novashop.novashopapi.Controller;

import cl.novashop.novashopapi.DTO.UsuarioDTO.*;
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

    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponse> login(@RequestBody UsuarioLoginRequest request) {
        try {
            UsuarioLoginResponse response = usuarioService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(new UsuarioLoginResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody UsuarioJpa usuario) {
        UsuarioResponse creado = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Long id) {
        UsuarioResponse encontrado = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(encontrado);
    }
}
