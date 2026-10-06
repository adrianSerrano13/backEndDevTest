package similar_products.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import similar_products.controller.ProductController;
import similar_products.dto.ProductDetail;
import similar_products.service.SimilarProductsService;

import java.util.List;

/*
 * Class for product controllers
 */

@RestController
public class ProductControllerImpl implements ProductController {

    private SimilarProductsService similarProductsService;

    @Autowired
    public ProductControllerImpl(SimilarProductsService similarProductsService){
        this.similarProductsService = similarProductsService;
    }

    @Override
    public Mono<List<ProductDetail>> getSimilarProducts(String productId) {
        return similarProductsService.getSimilarProducts(productId);
    }
}
