package bestcommerce.brand.product.service;

import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.repository.ProductRepository;
import bestcommerce.brand.product.repository.ProductRepositorySupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<ProductInfoDto> searchList(String managerEmail, String search){
        return productRepositorySupport.searchList(managerEmail, search);
    }

    public void productIdCheck(Long productId) throws RuntimeException{
        if(!productRepository.existsById(productId)){
            throw new RuntimeException(productId + " : Product Id is Not Exists");
        }
    }

    public void updateProduct(ProductInfoDto dto){
        productRepositorySupport.updateProductInfo(dto);
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

}
