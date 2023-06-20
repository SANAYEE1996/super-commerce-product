package bestcommerce.brand.product.dto;

import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.SizeDto;
import lombok.Getter;

import java.util.List;


@Getter
public class ProductDetailDto {

    private ProductInfoDto productInfoDto;

    private List<QuantityDto> quantityDtoList;

    private List<SizeDto> sizeDtoList;

    private List<ProductImageDto> productImageDtoList;

    public ProductDetailDto(ProductInfoDto productInfoDto, List<QuantityDto> quantityDtoList, List<SizeDto> sizeDtoList, List<ProductImageDto> productImageDtoList) {
        this.productInfoDto = productInfoDto;
        this.quantityDtoList = quantityDtoList;
        this.sizeDtoList = sizeDtoList;
        this.productImageDtoList = productImageDtoList;
    }
}
