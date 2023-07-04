package bestcommerce.brand.util;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDto {
    private Integer code;

    private String message;

    private ResponseBody<?> body;

}
