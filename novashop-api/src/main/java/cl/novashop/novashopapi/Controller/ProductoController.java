package cl.novashop.novashopapi.Controller;

import cl.novashop.novashopapi.DTO.ProductoDTO.ProductoRequest;
import cl.novashop.novashopapi.DTO.ProductoDTO.ProductoResponse;
import cl.novashop.novashopapi.Service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoResponse> getAll(){
        return productoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> getProductoById(@PathVariable Long id){
        return productoService.buscarPorId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(@RequestBody ProductoRequest productoRequest){
        ProductoResponse productoCreado = productoService.crear(productoRequest);
        return ResponseEntity.ok(productoCreado);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(
            @PathVariable Long id, @RequestBody ProductoRequest productoRequest){

        return productoService.actualizar(id,productoRequest).map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponse> eliminarProducto(@PathVariable Long id){
        productoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
