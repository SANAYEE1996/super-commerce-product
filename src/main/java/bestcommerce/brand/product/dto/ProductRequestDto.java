package bestcommerce.brand.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequestDto {

    private Long productId;

    private String search;

    private Long brandId;
}
