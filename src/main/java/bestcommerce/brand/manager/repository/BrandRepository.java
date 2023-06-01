package bestcommerce.brand.manager.repository;

import bestcommerce.brand.manager.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Transactional
    void deleteById(Long id);
}
