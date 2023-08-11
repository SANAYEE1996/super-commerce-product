package bestcommerce.brand.size.dto;

import lombok.Builder;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SizeDto {

    private Long sizeId;

    @NotNull(message = "quantityId must not Null")
    private Long quantityId;

    @NotBlank(message = "measureName must not Blank")
    private String measureName;

    private Long bodyId;

    @NotBlank(message = "bodyName must not Blank")
    private String bodyName;

    @NotBlank(message = "sizeValue must not Blank")
    private String sizeValue;

    public SizeDto(Long sizeId, Long quantityId, String measureName, Long bodyId, String bodyName, String sizeValue) {
        this.sizeId = sizeId;
        this.quantityId = quantityId;
        this.measureName = measureName;
        this.bodyId = bodyId;
        this.bodyName = bodyName;
        this.sizeValue = sizeValue;
    }
}
