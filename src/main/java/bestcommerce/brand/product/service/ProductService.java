package bestcommerce.brand.product.service;

import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.repository.ProductBulkRepository;
import bestcommerce.brand.product.repository.ProductRepository;
import bestcommerce.brand.product.repository.ProductRepositorySupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductRepositorySupport productRepositorySupport;

    private final ProductBulkRepository productBulkRepository;

    public Long save(Product product){
        return productRepository.save(product).getId();
    }

    public ProductInfoDto getDetailProduct(Long productId){
        return productRepositorySupport.getDetailProduct(productId);
    }

    public Product findProduct(Long productId){
        return productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Not enrolled Product Id"));
    }

    public List<ProductInfoDto> getProductList(Long brandId){
        return productRepositorySupport.getProductList(brandId);
    }

    public List<ProductInfoDto> searchList(Long brandId, String search){
        return productRepositorySupport.searchList(brandId, search);
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

    public void saveAll(List<ProductInfoDto> productInfoDtoList){
        productBulkRepository.saveAll(productInfoDtoList);
    }
}
