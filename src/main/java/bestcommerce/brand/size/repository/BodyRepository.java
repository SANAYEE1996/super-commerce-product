package bestcommerce.brand.size.repository;

import bestcommerce.brand.size.entity.Body;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodyRepository extends JpaRepository<Body, Long> {

    boolean existsByName(String name);

    Optional<Body> findByName(String name);
}
