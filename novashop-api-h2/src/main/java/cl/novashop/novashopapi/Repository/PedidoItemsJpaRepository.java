package cl.novashop.novashopapi.Repository;

import cl.novashop.novashopapi.Model.PedidoItemsJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoItemsJpaRepository extends JpaRepository<PedidoItemsJpa, Long> {
    List<PedidoItemsJpa> findByPedidoId(Long pedidoId);
}
