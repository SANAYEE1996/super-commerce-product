package bestcommerce.brand.size.dto;

import lombok.Getter;

@Getter
public class QuantityDto {

    private Long productId;

    private String quantityName;

    private int quantity;

    public QuantityDto(String quantityName, int quantity) {
        this.quantityName = quantityName;
        this.quantity = quantity;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
}
