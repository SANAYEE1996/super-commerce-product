package bestcommerce.brand.manager.repository;

import bestcommerce.brand.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByManagerEmail(String email);

    Boolean existsByManagerEmail(String email);
}
