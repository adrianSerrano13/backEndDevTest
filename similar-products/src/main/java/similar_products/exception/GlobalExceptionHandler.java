package similar_products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String PRODUCTO_NOT_FOUND_MSG = "Product not found";
    private static final String RETRIEVING_PRODUCT_ERROR_MSG = "Unable to retrieve product information";
    private static final String ERROR_LOG = "error";

    @ExceptionHandler(ProductClientException.class)
    public ResponseEntity<Map<String, String>> handleProductClientException(
            ProductClientException exception) {

        HttpStatus status = exception.getStatusCode() != null
                ? HttpStatus.valueOf(exception.getStatusCode().value())
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(Map.of(
                        ERROR_LOG,
                        status == HttpStatus.NOT_FOUND
                                ? PRODUCTO_NOT_FOUND_MSG
                                : RETRIEVING_PRODUCT_ERROR_MSG
                ));
    }
}
