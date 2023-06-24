package bestcommerce.brand.product.service;

import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.repository.ProductRepository;
import bestcommerce.brand.product.repository.ProductRepositorySupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductRepositorySupport productRepositorySupport;

    public Long save(Product product){
        return productRepository.save(product).getId();
    }

    public ProductInfoDto getDetailProduct(Long productId){
        return productRepositorySupport.getDetailProductDetail(productId);
    }

    public Product findProduct(Long productId){
        return productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Not enrolled Product Id"));
    }

    public List<ProductInfoDto> getProductList(String managerEmail){
        return productRepositorySupport.getProductList(managerEmail);
    }
}
