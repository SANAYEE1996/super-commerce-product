package bestcommerce.brand.product.service;

import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Long save(Product product){
        return productRepository.save(product).getId();
    }

    public void delete(Long productId){
        productRepository.deleteById(productId);
    }
}
