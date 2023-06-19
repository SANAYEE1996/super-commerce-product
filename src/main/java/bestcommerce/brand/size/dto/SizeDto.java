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
}
