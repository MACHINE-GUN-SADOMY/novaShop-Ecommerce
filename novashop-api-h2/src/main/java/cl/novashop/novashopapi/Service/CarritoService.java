package cl.novashop.novashopapi.Service;

import cl.novashop.novashopapi.DTO.CarritoDTO.CarritoActualizarCantidadRequest;
import cl.novashop.novashopapi.DTO.CarritoDTO.CarritoAgregarRequest;
import cl.novashop.novashopapi.DTO.CarritoDTO.CarritoResponse;
import cl.novashop.novashopapi.DTO.CarritoItemDTO.CarritoItemResponse;
import cl.novashop.novashopapi.Model.CarritoEstado;
import cl.novashop.novashopapi.Model.CarritoItemJpa;
import cl.novashop.novashopapi.Model.CarritoJpa;
import cl.novashop.novashopapi.Model.ProductoJpa;
import cl.novashop.novashopapi.Repository.CarritoItemsJpaRepository;
import cl.novashop.novashopapi.Repository.CarritoJpaRepository;
import cl.novashop.novashopapi.Repository.ProductoJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {
    private final CarritoJpaRepository carritoJpaRepository;
    private final CarritoItemsJpaRepository carritoItemJpa;
    private final ProductoJpaRepository productoJpaRepository;

    public CarritoService(CarritoJpaRepository carritoJpaRepository, CarritoItemsJpaRepository carritoItemJpa, ProductoJpaRepository productoJpaRepository) {
        this.carritoJpaRepository = carritoJpaRepository;
        this.carritoItemJpa = carritoItemJpa;
        this.productoJpaRepository = productoJpaRepository;
    }

    public CarritoResponse actualizarCantidadItem(Long itemId, CarritoActualizarCantidadRequest request) {
        Integer nuevaCantidad = request.getCantidad();
        if (nuevaCantidad == null) {
            throw new IllegalArgumentException("La cantidad no puede ser null");
        }

        CarritoItemJpa item = carritoItemJpa.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado con id: " + itemId));

        Long carritoId = item.getCarritoId();

        if (nuevaCantidad <= 0) {
            carritoItemJpa.delete(item);
        } else {
            item.setCantidad(nuevaCantidad);
            carritoItemJpa.save(item);
        }

        CarritoJpa carrito = carritoJpaRepository.findById(carritoId)
                .orElseThrow(() -> new IllegalStateException("Carrito no encontrado con id: " + carritoId));

        carrito.setActualizadoEn(LocalDateTime.now());
        carritoJpaRepository.save(carrito);

        return construirCarritoResponse(carrito);
    }

    public CarritoResponse eliminarItem(Long itemId) {
        CarritoItemJpa item = carritoItemJpa.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado con id: " + itemId));

        Long carritoId = item.getCarritoId();
        carritoItemJpa.delete(item);

        CarritoJpa carrito = carritoJpaRepository.findById(carritoId)
                .orElseThrow(() -> new IllegalStateException("Carrito no encontrado con id: " + carritoId));

        carrito.setActualizadoEn(LocalDateTime.now());
        carritoJpaRepository.save(carrito);

        return construirCarritoResponse(carrito);
    }

    public CarritoResponse vaciarCarrito(Long carritoId) {
        CarritoJpa carrito = carritoJpaRepository.findById(carritoId)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con id: " + carritoId));

        List<CarritoItemJpa> items = carritoItemJpa.findByCarritoId(carritoId);
        carritoItemJpa.deleteAll(items);

        carrito.setActualizadoEn(LocalDateTime.now());
        carritoJpaRepository.save(carrito);

        return construirCarritoResponse(carrito);
    }

    public CarritoResponse agregarItem(CarritoAgregarRequest request) {
        Long usuarioId = request.getUsuarioId();
        Long productoId = request.getProductoId();
        Integer cantidad = request.getCantidad();

        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        CarritoJpa carrito = obtenerOCrearCarritoActivo(usuarioId);

        ProductoJpa producto = productoJpaRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id: " + productoId));

        if (producto.getStock() < request.getCantidad()) {
            throw new RuntimeException("No hay stock suficiente para este producto.");
        }

        producto.setStock(producto.getStock() - request.getCantidad());
        productoJpaRepository.save(producto);

        Optional<CarritoItemJpa> itemOpt =
                carritoItemJpa.findByCarritoIdAndProductoId(carrito.getId(), productoId);

        if (itemOpt.isPresent()) {
            CarritoItemJpa item = itemOpt.get();
            item.setCantidad(item.getCantidad() + cantidad);
            carritoItemJpa.save(item);
        } else {
            CarritoItemJpa nuevo = new CarritoItemJpa();
            nuevo.setCarritoId(carrito.getId());
            nuevo.setProductoId(productoId);
            nuevo.setCantidad(cantidad);
            nuevo.setPrecioUnitario(producto.getPrecio());
            carritoItemJpa.save(nuevo);
        }

        carrito.setActualizadoEn(LocalDateTime.now());
        carritoJpaRepository.save(carrito);

        return construirCarritoResponse(carrito);
    }

    private CarritoJpa obtenerOCrearCarritoActivo(Long usuarioId) {
        return carritoJpaRepository.findByUsuarioIdAndEstado(usuarioId, CarritoEstado.ACTIVO)
                .orElseGet(() -> {
                    CarritoJpa nuevo = new CarritoJpa();
                    nuevo.setUsuarioId(usuarioId);
                    nuevo.setEstado(CarritoEstado.ACTIVO);
                    LocalDateTime ahora = LocalDateTime.now();
                    nuevo.setCreadoEn(ahora);
                    nuevo.setActualizadoEn(ahora);
                    return carritoJpaRepository.save(nuevo);
                });
    }

    public CarritoResponse obtenerCarritoPorUsuario(Long usuarioId) {
        CarritoJpa carrito = obtenerOCrearCarritoActivo(usuarioId);
        return construirCarritoResponse(carrito);
    }

    private CarritoResponse construirCarritoResponse(CarritoJpa carrito) {
        List<CarritoItemJpa> items = carritoItemJpa.findByCarritoId(carrito.getId());

        CarritoResponse resp = new CarritoResponse();
        resp.setCarritoId(carrito.getId());
        resp.setUsuarioId(carrito.getUsuarioId());
        resp.setEstado(carrito.getEstado().name());

        BigDecimal total = BigDecimal.ZERO;

        List<CarritoItemResponse> itemResponses = items.stream().map(item -> {
            ProductoJpa producto = productoJpaRepository.findById(item.getProductoId())
                    .orElse(null);

            CarritoItemResponse ir = new CarritoItemResponse();
            ir.setItemId(item.getId());
            ir.setProductoId(item.getProductoId());
            ir.setCantidad(item.getCantidad());
            ir.setPrecioUnitario(item.getPrecioUnitario());

            String nombreProd = (producto != null) ? producto.getNombre() : "Producto eliminado";
            ir.setNombreProducto(nombreProd);

            BigDecimal subtotal = item.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(item.getCantidad()));
            ir.setSubtotal(subtotal);

            return ir;
        }).toList();

        for (CarritoItemResponse ir : itemResponses) {
            total = total.add(ir.getSubtotal());
        }

        resp.setItems(itemResponses);
        resp.setTotal(total);

        return resp;
    }
}