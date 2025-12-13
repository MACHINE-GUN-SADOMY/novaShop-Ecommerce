package cl.novashop.novashopapi.Controller;

import cl.novashop.novashopapi.DTO.CarritoDTO.CarritoActualizarCantidadRequest;
import cl.novashop.novashopapi.DTO.CarritoDTO.CarritoAgregarRequest;
import cl.novashop.novashopapi.DTO.CarritoDTO.CarritoResponse;
import cl.novashop.novashopapi.Service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {
    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarritoResponse> obtenerCarritoPorUsuario(@PathVariable Long usuarioId) {
        CarritoResponse carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/items")
    public ResponseEntity<CarritoResponse> agregarItem(@RequestBody CarritoAgregarRequest request) {
        CarritoResponse carrito = carritoService.agregarItem(request);
        return ResponseEntity.ok(carrito);
    }


    @PutMapping("/items/{itemId}")
    public ResponseEntity<CarritoResponse> actualizarCantidadItem(
            @PathVariable Long itemId,
            @RequestBody CarritoActualizarCantidadRequest request) {

        CarritoResponse carrito = carritoService.actualizarCantidadItem(itemId, request);
        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CarritoResponse> eliminarItem(@PathVariable Long itemId) {
        CarritoResponse carrito = carritoService.eliminarItem(itemId);
        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/{carritoId}")
    public ResponseEntity<CarritoResponse> vaciarCarrito(@PathVariable Long carritoId) {
        CarritoResponse carrito = carritoService.vaciarCarrito(carritoId);
        return ResponseEntity.ok(carrito);
    }
}
