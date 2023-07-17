package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
