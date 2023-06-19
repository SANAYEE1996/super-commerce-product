package bestcommerce.brand.product.service;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.repository.ProductRepository;
import bestcommerce.brand.product.repository.ProductRepositorySupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductRepositorySupport productRepositorySupport;

    public Long save(Product product){
        return productRepository.save(product).getId();
    }

    public void delete(Long productId){
        productRepository.deleteById(productId);
    }

    public ProductDetailDto getDetailProduct(Long productId){
        return productRepositorySupport.getDetailProductDetail(productId);
    }
}
