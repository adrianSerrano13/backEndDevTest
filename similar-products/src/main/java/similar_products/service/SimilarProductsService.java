package similar_products.service;

import reactor.core.publisher.Mono;
import similar_products.dto.ProductDetail;

import java.util.List;

public interface SimilarProductsService {

    /*
     * Get similar products for a given product
     *
     * @param productId product identifier
     * @return list of {@lik ProductDetail}
     */
    Mono<List<ProductDetail>> getSimilarProducts(String productId);
}
