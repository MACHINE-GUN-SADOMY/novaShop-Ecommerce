package cl.novashop.novashopapi.Repository;

import cl.novashop.novashopapi.Model.UsuarioJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository <UsuarioJpa, Long> {
    @Query("SELECT u FROM UsuarioJpa u WHERE u.email = :email AND u.password = :password")
    Optional<UsuarioJpa> login(@Param("email") String email,
                               @Param("password") String password);

    Optional<UsuarioJpa> findByEmailAndPassword(String email, String password);

}
