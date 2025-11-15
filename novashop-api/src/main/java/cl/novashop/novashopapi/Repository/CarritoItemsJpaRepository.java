package cl.novashop.novashopapi.Repository;

import cl.novashop.novashopapi.Model.CarritoItemJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoItemsJpaRepository extends JpaRepository<CarritoItemJpa, Long> {
    List<CarritoItemJpa> findByCarritoId(Long carritoId);
    Optional<CarritoItemJpa> findByCarritoIdAndProductoId(Long carritoId, Long productoId);
}
