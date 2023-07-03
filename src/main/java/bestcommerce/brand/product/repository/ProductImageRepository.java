package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductOrderByOdr(Product product);

    @Query("select count(*) from product_img p where p.product.id = :id")
    int checkImageExistsByProductId(Long id);
}
