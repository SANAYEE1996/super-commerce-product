package bestcommerce.brand.size.dto;

import lombok.Getter;

@Getter
public class BodyDto {

    private Long bodyId;

    private Long bodyName;

    public BodyDto(Long bodyId, Long bodyName) {
        this.bodyId = bodyId;
        this.bodyName = bodyName;
    }
}
