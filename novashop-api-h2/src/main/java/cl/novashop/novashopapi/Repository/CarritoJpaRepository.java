package cl.novashop.novashopapi.Repository;

import cl.novashop.novashopapi.Model.CarritoEstado;
import cl.novashop.novashopapi.Model.CarritoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoJpaRepository extends JpaRepository<CarritoJpa, Long> {
    Optional<CarritoJpa> findByUsuarioIdAndEstado(Long usuarioId, CarritoEstado estado);
}
