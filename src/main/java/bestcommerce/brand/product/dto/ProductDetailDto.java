package bestcommerce.brand.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDetailDto {

    private Long id;

    private String productCode;

    private String productName;

    private int productPrice;

    private int productInfo;

    private String productRegisterDto;

    private Long brandId;

    private String brandName;

    private String brandLogoImage;
}
