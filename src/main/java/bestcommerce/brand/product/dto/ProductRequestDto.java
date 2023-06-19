package bestcommerce.brand.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequestDto {

    private String managerEmail;

    private Long productId;

    private String search;
}
