package bestcommerce.brand.size.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuantityDto {

    private Long quantityId;

    private Long productId;

    private String quantityName;

    private int quantity;

    public QuantityDto(Long quantityId, Long productId, String quantityName, int quantity) {
        this.quantityId = quantityId;
        this.productId = productId;
        this.quantityName = quantityName;
        this.quantity = quantity;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
}
