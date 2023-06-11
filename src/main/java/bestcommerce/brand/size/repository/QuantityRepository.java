package bestcommerce.brand.size.repository;

import bestcommerce.brand.size.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {
}
