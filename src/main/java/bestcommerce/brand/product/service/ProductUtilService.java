package bestcommerce.brand.product.service;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductUtilService {

    private final ProductService productService;

    private final QuantityService quantityService;

    private final SizeService sizeService;

    private final ProductImageService productImageService;

    private final DtoConverter dtoConverter;

    public ProductDetailDto findProductDetail(Long productId) throws RuntimeException{
        Product product = productService.findProduct(productId);
        ProductInfoDto productInfoDto = productService.getDetailProduct(productId);
        List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findQuantityList(product));
        List<SizeDto> sizeDtoList = sizeService.getSizeList(productId);
        List<ProductImageDto> productImageDtoList = dtoConverter.toProductImageDtoList(productImageService.getProductImageList(product));
        return new ProductDetailDto(productInfoDto, quantityDtoList, sizeDtoList, productImageDtoList);
    }
}
