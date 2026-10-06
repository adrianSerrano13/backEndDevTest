package similar_products.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import similar_products.client.ProductClient;
import similar_products.dto.ProductDetail;
import similar_products.service.SimilarProductsService;

import java.util.List;

@Service
public class SimilarProductsServiceImpl implements SimilarProductsService {

    private ProductClient productClient;

    @Autowired
    public SimilarProductsServiceImpl(ProductClient productClient){

        this.productClient = productClient;
    }

    @Override
    public Mono<List<ProductDetail>> getSimilarProducts(String productId) {
        return productClient.getSimilarProductIds(productId)
                .flatMap(productClient::getProducts);
    }
}