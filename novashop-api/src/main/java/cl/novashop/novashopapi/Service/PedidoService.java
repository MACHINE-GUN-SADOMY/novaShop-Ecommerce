package cl.novashop.novashopapi.Service;

import cl.novashop.novashopapi.DTO.PedidoDTO.PedidoItemsRequest;
import cl.novashop.novashopapi.DTO.PedidoDTO.PedidoItemsResponse;
import cl.novashop.novashopapi.DTO.PedidoDTO.PedidoRequest;
import cl.novashop.novashopapi.DTO.PedidoDTO.PedidoResponse;
import cl.novashop.novashopapi.Model.CarritoItemJpa;
import cl.novashop.novashopapi.Model.PedidoItemsJpa;
import cl.novashop.novashopapi.Model.PedidoJpa;
import cl.novashop.novashopapi.Repository.CarritoItemsJpaRepository;
import cl.novashop.novashopapi.Repository.PedidoItemsJpaRepository;
import cl.novashop.novashopapi.Repository.PedidosJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidosJpaRepository pedidoJpaRepository;
    private final PedidoItemsJpaRepository pedidoItemsJpaRepository;
    private final CarritoItemsJpaRepository carritoItemsJpaRepository;

    public PedidoService(PedidosJpaRepository pedidoJpaRepository,
                         PedidoItemsJpaRepository pedidoItemJpaRepository, PedidoItemsJpaRepository pedidoItemsJpaRepository, CarritoItemsJpaRepository carritoItemsJpaRepository) {
        this.pedidoJpaRepository = pedidoJpaRepository;
        this.pedidoItemsJpaRepository = pedidoItemsJpaRepository;
        this.carritoItemsJpaRepository = carritoItemsJpaRepository;
    }

    // CANCELAR EL PEDIDO
    @Transactional
    public PedidoResponse cancelarPedido(Long pedidoId, Long usuarioId) {
        Optional<PedidoJpa> optionalPedido = pedidoJpaRepository.findById(pedidoId);

        if (optionalPedido.isEmpty()) {
            return null;
        }

        PedidoJpa pedido = optionalPedido.get();

        if (!pedido.getUsuarioId().equals(usuarioId)) {
            throw new RuntimeException("El pedido no pertenece al usuario.");
        }

        if (!"PENDIENTE".equalsIgnoreCase(pedido.getEstado())) {
            throw new RuntimeException("Solo se pueden cancelar pedidos pendientes.");
        }

        pedido.setEstado("CANCELADO");
        PedidoJpa actualizado = pedidoJpaRepository.save(pedido);

        List<PedidoItemsJpa> items = pedidoItemsJpaRepository.findByPedidoId(pedidoId);

        return buildPedidoResponse(actualizado, items);
    }

    // CREAR PEDIDO , TOMA EN CONSIDERACION LO DE CARRITO
    @Transactional
    public PedidoResponse crearPedido(PedidoRequest request) {
        List<CarritoItemJpa> carritoItems =
                carritoItemsJpaRepository.findByCarritoId(request.getCarritoId());

        if (carritoItems == null || carritoItems.isEmpty()) {
            throw new RuntimeException("El carrito está vacío. No se puede crear un pedido.");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CarritoItemJpa item : carritoItems) {
            if (item.getPrecioUnitario() != null && item.getCantidad() != null) {
                BigDecimal subtotal = item.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(item.getCantidad()));
                total = total.add(subtotal);
            }
        }

        PedidoJpa pedido = new PedidoJpa();
        pedido.setUsuarioId(request.getUsuarioId());
        pedido.setCarritoId(request.getCarritoId());
        pedido.setDireccionEnvio(request.getDireccionEnvio());
        pedido.setTotal(total);
        pedido.setEstado("PENDIENTE");

        PedidoJpa pedidoGuardado = pedidoJpaRepository.save(pedido);

        List<PedidoItemsJpa> pedidoItems = new ArrayList<>();
        for (CarritoItemJpa ci : carritoItems) {
            PedidoItemsJpa pi = new PedidoItemsJpa();
            pi.setPedidoId(pedidoGuardado.getId());
            pi.setProductoId(ci.getProductoId());
            pi.setCantidad(ci.getCantidad());
            pi.setPrecioUnitario(ci.getPrecioUnitario());
            pedidoItems.add(pi);
        }

        List<PedidoItemsJpa> itemsGuardados = pedidoItemsJpaRepository.saveAll(pedidoItems);

        return buildPedidoResponse(pedidoGuardado, itemsGuardados);
    }

    // CALCULAR EL TOTAL DEL PEDIDO
    private BigDecimal calcularTotal(List<PedidoItemsRequest> items) {
        if (items == null) return BigDecimal.ZERO;

        BigDecimal total = BigDecimal.ZERO;

        for (PedidoItemsRequest item : items) {
            if (item.getPrecioUnitario() != null && item.getCantidad() != null) {
                BigDecimal subtotal = item.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(item.getCantidad()));
                total = total.add(subtotal);
            }
        }
        return total;
    }

    // OBTENER PEDIDO POR ID PEDIDO
    public PedidoResponse obtenerPedidoPorId    (Long id) {
        Optional<PedidoJpa> optionalPedido = pedidoJpaRepository.findById(id);

        PedidoJpa pedido = optionalPedido.orElse(null);

        if (pedido == null) {
            return null;
        }

        List<PedidoItemsJpa> items = pedidoItemsJpaRepository.findByPedidoId(id);

        return buildPedidoResponse(pedido, items);
    }

    // OBTENER PEDIDO POR ID DE USUARIO
    public List<PedidoResponse> obtenerPedidosPorUsuario(Long usuarioId) {
        List<PedidoJpa> pedidos = pedidoJpaRepository.findByUsuarioId(usuarioId);

        List<PedidoResponse> responses = new ArrayList<>();

        for (PedidoJpa pedido : pedidos) {
            List<PedidoItemsJpa> items = pedidoItemsJpaRepository.findByPedidoId(pedido.getId());
            PedidoResponse response = buildPedidoResponse(pedido, items);
            responses.add(response);
        }

        return responses;
    }

    // BUILDER MAPPER
    private PedidoResponse buildPedidoResponse(PedidoJpa pedido, List<PedidoItemsJpa> items) {

        PedidoResponse response = new PedidoResponse();

        response.setId(pedido.getId());
        response.setUsuarioId(pedido.getUsuarioId());
        response.setTotal(pedido.getTotal());
        response.setEstado(pedido.getEstado());
        response.setDireccionEnvio(pedido.getDireccionEnvio());
        response.setCreadoEn(pedido.getCreadoEn());
        response.setCarritoId(pedido.getCarritoId());

        List<PedidoItemsResponse> itemsResponse = new ArrayList<>();

        if (items != null) {
            for (PedidoItemsJpa item : items) {
                PedidoItemsResponse itemRes = new PedidoItemsResponse();

                itemRes.setId(item.getId());
                itemRes.setPedidoId(item.getPedidoId());
                itemRes.setProductoId(item.getProductoId());
                itemRes.setCantidad(item.getCantidad());
                itemRes.setPrecioUnitario(item.getPrecioUnitario());

                itemsResponse.add(itemRes);
            }
        }
        response.setItems(itemsResponse);
        return response;
    }

    // PAGO SIMULADO
    @Transactional
    public PedidoResponse pagarPedido(Long id) {
        Optional<PedidoJpa> optionalPedido = pedidoJpaRepository.findById(id);
        if (optionalPedido.isEmpty()) {
            return null;
        }

        PedidoJpa pedido = optionalPedido.get();

        if (!"PENDIENTE".equalsIgnoreCase(pedido.getEstado())) {
            return null;
        }

        // VALIDA QUE EL PEDIDO SEA DEL USUARIO
        if (!pedido.getUsuarioId().equals(pedido.getUsuarioId())) {
            throw new RuntimeException("El pedido no pertenece al usuario.");
        }

        pedido.setEstado("PAGADO");
        PedidoJpa pedidoActualizado = pedidoJpaRepository.save(pedido);

        List<PedidoItemsJpa> items = pedidoItemsJpaRepository.findByPedidoId(id);

        // ESTO ES PARA VACIAR EL CARRITO
        List<CarritoItemJpa> carritoItems = carritoItemsJpaRepository.findByCarritoId(pedido.getCarritoId());
        if (carritoItems != null && !carritoItems.isEmpty()) {
            carritoItemsJpaRepository.deleteAll(carritoItems);
        }

        return buildPedidoResponse(pedidoActualizado, items);
    }
}
