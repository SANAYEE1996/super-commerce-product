package bestcommerce.brand.size.repository;

import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.size.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {

    List<Quantity> findAllByProduct(Product product);
}
