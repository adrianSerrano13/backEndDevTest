package similar_products.client.impl;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import similar_products.client.ProductClient;
import similar_products.dto.ProductDetail;

import reactor.netty.http.client.HttpClient;
import similar_products.exception.ProductClientException;

import java.time.Duration;
import java.util.List;

@Component
public class ProductClientImpl implements ProductClient {

    private WebClient client;

    private static final String PRODUCT_NOT_FOUND_MSG = "Product not found: ";
    private static final String RETRIEVING_PRODUCT_ERROR_MSG = "Error retrieving product: ";
    private static final String REQUEST_PRODUCT = "/product";
    private static final String REQUEST_SIMILARDS = "/similarids";
    private static final String PRODUCT_ID_URI = "/{productId}";
    private final int maxConcurrency;


    public ProductClientImpl(
            @Value("${similar-products.client.base-url}") String baseUrl,
            @Value("${similar-products.client.max-concurrency}") int maxConcurrency,
            @Value("${similar-products.client.response-timeout}") Duration responseTimeout,
            @Value("${similar-products.client.connect-timeout-millis}") int connectTimeoutMillis) {

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis)
                .responseTimeout(responseTimeout);

        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        this.maxConcurrency = maxConcurrency;
    }

    @Override
    public Mono<List<String>> getSimilarProductIds(String productId) {

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(new StringBuilder()
                                .append(REQUEST_PRODUCT)
                                .append(PRODUCT_ID_URI)
                                .append(REQUEST_SIMILARDS)
                                .toString())
                        .build(productId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {});
    }

    @Override
    public Mono<List<ProductDetail>> getProducts(List<String> productIds) {

        return Flux.fromIterable(productIds)
                .flatMapSequential(this::getProduct, maxConcurrency)
                .collectList();
    }

    private Mono<ProductDetail> getProduct(String productId) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                    .path(new StringBuilder()
                            .append(REQUEST_PRODUCT)
                            .append(PRODUCT_ID_URI)
                            .toString())
                    .build(productId))
            .retrieve()
            .onStatus(
                    status -> status.value() == 404,
                    response -> response.createException()
                            .map(exception -> new ProductClientException(
                                    PRODUCT_NOT_FOUND_MSG + productId,
                                    exception,
                                    response.statusCode()
                            ))
            )
            .onStatus(
                    status -> status.is5xxServerError(),
                    response -> response.createException()
                            .map(exception -> new ProductClientException(
                                    RETRIEVING_PRODUCT_ERROR_MSG + productId,
                                    exception,
                                    response.statusCode()
                            ))
            )
            .bodyToMono(ProductDetail.class)
            .onErrorMap(
                    error -> !(error instanceof ProductClientException),
                    error -> new ProductClientException(
                            RETRIEVING_PRODUCT_ERROR_MSG + productId,
                            error,
                            null
                    )
            );
    }
}
