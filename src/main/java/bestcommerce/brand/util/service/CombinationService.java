package bestcommerce.brand.util.service;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.converter.DtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CombinationService {

    public ProductDetailDto findProductDetail(ProductService productService,
                                              QuantityService quantityService,
                                              SizeService sizeService,
                                              ProductImageService productImageService,
                                              DtoConverter dtoConverter,
                                              Long productId) throws RuntimeException{
        Product product = productService.findProduct(productId);
        ProductInfoDto productInfoDto = productService.getDetailProduct(productId);
        List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findQuantityList(product));
        List<SizeDto> sizeDtoList = sizeService.getSizeList(productId);
        List<ProductImageDto> productImageDtoList = dtoConverter.toProductImageDtoList(productImageService.getProductImageList(product));
        return new ProductDetailDto(productInfoDto, quantityDtoList, sizeDtoList, productImageDtoList);
    }
}
