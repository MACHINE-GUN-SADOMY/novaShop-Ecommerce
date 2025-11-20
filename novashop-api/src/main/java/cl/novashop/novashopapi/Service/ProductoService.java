package cl.novashop.novashopapi.Service;

import cl.novashop.novashopapi.DTO.ProductoDTO.ProductoRequest;
import cl.novashop.novashopapi.DTO.ProductoDTO.ProductoResponse;
import cl.novashop.novashopapi.Model.ProductoJpa;
import cl.novashop.novashopapi.Repository.ProductoJpaRepository;
import org.springframework.stereotype.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoJpaRepository productoJpaRepository;

    public ProductoService(ProductoJpaRepository productoJpaRepository) {
        this.productoJpaRepository = productoJpaRepository;
    }

    public List<ProductoResponse> listarTodos(){
        return productoJpaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ProductoResponse buscarPorId(Long id) {
        ProductoJpa producto = productoJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        return new ProductoResponse(
              producto.getId(),
              producto.getNombre(),
              producto.getDescripcion(),
              producto.getPrecio(),
              producto.getStock(),
              producto.getCategoriId());
    }

    public ProductoResponse crear(ProductoRequest request) {
        ProductoJpa p = new ProductoJpa();
        p.setNombre(request.getNombre());
        p.setDescripcion(request.getDescripcion());
        p.setPrecio(request.getPrecio());
        p.setStock(request.getStock());
        p.setCategoriId(request.getCategoriId());

        ProductoJpa guardado = productoJpaRepository.save(p);
        return toResponse(guardado);
    }

    public Optional<ProductoResponse> actualizar(Long id, ProductoRequest productoRequest) {
        return productoJpaRepository.findById(id).map(existente -> {
            existente.setNombre(productoRequest.getNombre());
            existente.setDescripcion(productoRequest.getDescripcion());
            existente.setPrecio(productoRequest.getPrecio());
            existente.setStock(productoRequest.getStock());
            existente.setCategoriId(productoRequest.getCategoriId());
            return toResponse(productoJpaRepository.save(existente));
        });
    }

    public void eliminar(Long id){
        productoJpaRepository.deleteById(id);
    }


    private ProductoResponse toResponse(ProductoJpa p) {
        return new ProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoriId()
        );
    }
}
