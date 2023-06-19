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

    private String productInfo;

    private String productRegisterDate;

    private Long brandId;

    private String brandName;

    private String brandLogoImage;

    public ProductDetailDto(Long id, String productCode, String productName, int productPrice, String productInfo, String productRegisterDate, Long brandId, String brandName, String brandLogoImage) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
        this.productRegisterDate = productRegisterDate;
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandLogoImage = brandLogoImage;
    }
}
