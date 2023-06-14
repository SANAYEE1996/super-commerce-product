package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProduct(Product product);
}
