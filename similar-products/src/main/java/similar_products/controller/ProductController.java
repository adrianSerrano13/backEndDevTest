package similar_products.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import similar_products.dto.ProductDetail;

import java.util.List;

/*
 *  ProductController
 *
 * Interface that defines the REST API of products
 *
 * @Since 06-10-2026
 */
@RestController
@RequestMapping("/product")
public interface ProductController {

    /*
     * Get similar products for a given product
     *
     * @param productId product identifier
     * @return list of {@link ProductDetail}
     */
    @Operation(summary = "Get similar products for a given product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not found")
    })
    @GetMapping(value = "/{productId}/similar", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<ProductDetail>> getSimilarProducts(
            @Parameter(name = "productId", description = "product identifier", required = true)
            @PathVariable String productId);

}
