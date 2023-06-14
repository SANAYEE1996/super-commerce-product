package bestcommerce.brand.product.dto;

import lombok.Getter;

@Getter
public class ProductImageDto {

    private Long productId;

    private String type;

    private String image;

    private int odr;

    public ProductImageDto(Long productId, String type, String image, int odr) {
        this.productId = productId;
        this.type = type;
        this.image = image;
        this.odr = odr;
    }
}
