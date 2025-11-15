package cl.novashop.novashopapi.Repository;

import cl.novashop.novashopapi.Model.ProductoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoJpaRepository extends JpaRepository<ProductoJpa, Long> {
    // Por ahora un crud basico
}
