package bestcommerce.brand.manager.repository;

import bestcommerce.brand.manager.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
