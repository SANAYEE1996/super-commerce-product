package bestcommerce.brand.util;

import lombok.Builder;

@Builder
public class ResponseDto {
    private String message;

    private ResponseStatus responseStatus;
}
