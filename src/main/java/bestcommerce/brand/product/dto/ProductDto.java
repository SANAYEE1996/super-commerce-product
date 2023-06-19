package bestcommerce.brand.product.dto;

import bestcommerce.brand.size.dto.SizeDto;
import lombok.Getter;

import java.util.List;


@Getter
public class ProductDto {

    private ProductDetailDto productDetailDto;

    private List<SizeDto> sizeDtoList;

    public ProductDto(ProductDetailDto productDetailDto, List<SizeDto> sizeDtoList) {
        this.productDetailDto = productDetailDto;
        this.sizeDtoList = sizeDtoList;
    }
}
