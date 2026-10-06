package similar_products.exception;

import org.springframework.http.HttpStatusCode;

public class ProductClientException extends RuntimeException{

    private final HttpStatusCode statusCode;

    public ProductClientException(
            String message,
            Throwable cause,
            HttpStatusCode statusCode) {

        super(message, cause);
        this.statusCode = statusCode;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
