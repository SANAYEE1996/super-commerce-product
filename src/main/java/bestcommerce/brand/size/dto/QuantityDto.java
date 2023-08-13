package bestcommerce.brand.size.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDto {

    private Long quantityId;

    @NotNull(message = "productId must not Null")
    private Long productId;

    @NotBlank(message = "quantityName must not Blank")
    private String quantityName;

    private int quantity;

    public void setProductId(@NotNull Long productId){
        this.productId = productId;
    }
}
