package bestcommerce.brand.util;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    OK(200,"OK"),
    EXCEPTION(500,"EXCEPTION");

    private final int statusCode;
    private final String statusMessage;

    ResponseStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
