package cl.novashop.novashopapi.Repository;

import cl.novashop.novashopapi.Model.PedidoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidosJpaRepository extends JpaRepository<PedidoJpa, Long> {
    List<PedidoJpa> findByUsuarioId(Long usuarioId);
}
