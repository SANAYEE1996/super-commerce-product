package bestcommerce.brand.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductImageDto {

    private Long productId;

    private Long imageId;

    private String type;

    private String image;

    private int odr;

    public ProductImageDto(Long productId, Long imageId, String type, String image, int odr) {
        this.productId = productId;
        this.imageId = imageId;
        this.type = type;
        this.image = image;
        this.odr = odr;
    }
}
