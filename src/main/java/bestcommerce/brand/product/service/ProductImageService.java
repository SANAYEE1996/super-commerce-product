package bestcommerce.brand.product.service;

import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.entity.ProductImage;
import bestcommerce.brand.product.repository.ProductImageBulkRepository;
import bestcommerce.brand.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    private final ProductImageBulkRepository productImageBulkRepository;

    public void saveAll(List<ProductImageDto> productImageDtoList){
        productImageBulkRepository.saveAll(productImageDtoList);
    }

    public List<ProductImage> getProductImageList(Product product){
        return productImageRepository.findAllByProductOrderByOdr(product);
    }

    public void updateAll(List<ProductImageDto> productImageDtoList){
        productImageBulkRepository.updateAll(productImageDtoList);
    }

    public void deleteAll(List<ProductImageDto> productImageDtoList){
        productImageBulkRepository.deleteAll(productImageDtoList);
    }
}
