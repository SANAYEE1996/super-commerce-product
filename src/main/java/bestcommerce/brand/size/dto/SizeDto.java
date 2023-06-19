package bestcommerce.brand.size.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SizeDto {

    private Long sizeId;

    private Long quantityId;

    private String measureName;

    private Long bodyId;

    private String bodyName;

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
