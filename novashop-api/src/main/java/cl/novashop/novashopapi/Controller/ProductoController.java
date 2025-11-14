package cl.novashop.novashopapi.Controller;

import cl.novashop.novashopapi.DTO.ProductoDTO.ProductoRequest;
import cl.novashop.novashopapi.DTO.ProductoDTO.ProductoResponse;
import cl.novashop.novashopapi.Model.ProductoJpa;
import cl.novashop.novashopapi.Model.RolUsuario;
import cl.novashop.novashopapi.Model.UsuarioJpa;
import cl.novashop.novashopapi.Repository.UsuarioJpaRepository;
import cl.novashop.novashopapi.Service.ProductoService;
import cl.novashop.novashopapi.Service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "")
public class ProductoController {
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public ProductoController(ProductoService productoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(
            @RequestParam Long userId, @RequestBody ProductoRequest req) {
        try {
            usuarioService.validarAdmin(userId);

            ProductoResponse creado = productoService.crear(req);

            return ResponseEntity.ok(creado);

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(
            @RequestParam Long userId,
            @PathVariable Long id,
            @RequestBody ProductoRequest request) {

        try {
            usuarioService.validarAdmin(userId);

            return productoService.actualizar(id, request)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminar(
            @RequestParam Long userId,
            @PathVariable Long id) {

        try {
            usuarioService.validarAdmin(userId);

            productoService.eliminar(id);

            return ResponseEntity.ok("Producto eliminado con éxito");

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
