package bestcommerce.brand.product.service;

import bestcommerce.brand.product.dto.*;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.converter.DtoConverter;
import bestcommerce.brand.util.converter.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCombineService {

    private final ProductService productService;

    private final BrandService brandService;

    private final ProductImageService productImageService;

    private final QuantityService quantityService;

    private final SizeService sizeService;

    private final EntityConverter entityConverter;

    private final DtoConverter dtoConverter;

    public Long saveProduct(ProductCreateDto productCreateDto){
        List<QuantityDto> quantityDtoList = productCreateDto.getQuantityDtoList();
        Long productId = productService.save(entityConverter.toProductEntity(productCreateDto, brandService.findBrand(productCreateDto.getBrandId())));
        for(QuantityDto quantityDto : quantityDtoList){
            quantityDto.setProductId(productId);
        }
        quantityService.saveAll(quantityDtoList);
        return productId;
    }

    public ProductDetailDto getDetailProduct(Long productId) throws RuntimeException{
        Product product = productService.findProduct(productId);
        ProductInfoDto productInfoDto = productService.getDetailProduct(productId);
        List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findQuantityList(product));
        List<SizeDto> sizeDtoList = sizeService.getSizeList(productId);
        List<ProductImageDto> productImageDtoList = dtoConverter.toProductImageDtoList(productImageService.getProductImageList(product));
        return new ProductDetailDto(productInfoDto, quantityDtoList, sizeDtoList, productImageDtoList);
    }

    public List<ProductInfoDto> getProductList(ProductRequestDto dto){
        return productService.getProductList(dto.getBrandId());
    }

    public List<ProductInfoDto> getProductSearchList(ProductRequestDto dto){
        return productService.searchList(dto.getBrandId(), dto.getSearch());
    }

    public void updateProduct(ProductInfoDto dto) throws RuntimeException{
        productService.productIdCheck(dto.getId());
        productService.updateProduct(dto);
    }

    public void deleteProduct(ProductRequestDto dto) throws RuntimeException{
        productService.productIdCheck(dto.getProductId());
        quantityService.checkQuantityExistsByProductId(dto.getProductId());
        productImageService.checkImageExistsByProductId(dto.getProductId());
    }

    public void deleteProduct(Long productId){
        productService.deleteProduct(productId);
    }
}
