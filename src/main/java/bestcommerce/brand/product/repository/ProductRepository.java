package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByIdIsGreaterThan(Long id);
}
