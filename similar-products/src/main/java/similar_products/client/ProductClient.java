package similar_products.client;

import reactor.core.publisher.Mono;
import similar_products.dto.ProductDetail;

import java.util.List;

public interface ProductClient {

    /*
     * Get similar products from a product identifier
     *
     * @param  productId product identifier
     * @return list of products identifiers
     */
    Mono<List<String>> getSimilarProductIds(String productId);

    /*
     * Get products details from a products identifiers
     *
     * @param  productId product identifier
     *  @return list of {@link ProductDetail}
     */
    Mono<List<ProductDetail>> getProducts(List<String> productIds);
}
