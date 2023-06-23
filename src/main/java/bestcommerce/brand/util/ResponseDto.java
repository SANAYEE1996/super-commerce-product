package bestcommerce.brand.util;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDto {
    private String message;

    private ResponseStatus responseStatus;

    private Long productId;
}
