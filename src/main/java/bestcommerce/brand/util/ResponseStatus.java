package bestcommerce.brand.util;

public enum ResponseStatus {

    OK(200,"OK"),
    EXCEPTION(500,"EXCEPTION");

    int statusCode;
    String code;

    ResponseStatus(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
