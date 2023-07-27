package bestcommerce.brand.size.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityModifyDto {

    @NotNull(message = "quantityId must not Null")
    private Long quantityId;

    private String quantityName;

    private int quantity;
}
