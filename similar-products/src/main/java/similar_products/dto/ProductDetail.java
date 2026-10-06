package similar_products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "Product detail")
public class ProductDetail {

    @Schema(description = "Product identifier", example = "1")
    private String id;

    @Schema(description = "Product name", example = "Shirt")
    private String name;

    @Schema(description = "Product price", example = "9.99")
    private double price;

    @Schema(description = "Whether the product is available", example = "true")
    private boolean availability;
}
