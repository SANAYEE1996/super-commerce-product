package bestcommerce.brand.size.repository;

import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.size.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {

    List<Quantity> findAllByProduct(Product product);

    @Transactional
    @Modifying
    @Query("update quantity q set q.measureName = :name , q.remain = :remain where q.id = :id ")
    void updateNameRemainQuantity(String name, int remain, Long id);

    @Transactional
    @Modifying
    @Query("update quantity q set q.remain = :remain where q.id = :id ")
    void updateRemainQuantity(int remain, Long id);

    @Query("select q from quantity  q where q.product.id = :id")
    List<Quantity> findAllQuantityByProductId(Long id);

    @Query("select count(*) from quantity q where q.product.id = :id")
    int checkQuantityExistsByProductId(Long id);

    List<Quantity> findAllByIdIsGreaterThanEqualOrderById(Long id);
}
