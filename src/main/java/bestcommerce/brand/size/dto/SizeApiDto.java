package bestcommerce.brand.size.dto;

import bestcommerce.brand.product.dto.ProductRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class SizeApiDto {

    private ProductRequestDto productRequestDto;

    private List<SizeDto> sizeDtoList;

    public SizeApiDto(ProductRequestDto productRequestDto, List<SizeDto> sizeDtoList) {
        this.productRequestDto = productRequestDto;
        this.sizeDtoList = sizeDtoList;
    }
}
