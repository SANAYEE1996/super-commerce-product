package bestcommerce.brand.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseBody<T> {
    private final T data;
}
