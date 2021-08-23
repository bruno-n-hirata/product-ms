package com.compassouol.productms.core.mappers;

import com.compassouol.productms.core.domain.Product;
import com.compassouol.productms.core.ports.dto.ProductDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    private final String DEFAULT_NAME = "Product";
    private final String DEFAULT_DESCRIPTION = "Product Description";
    private final BigDecimal DEFAULT_PRICE = new BigDecimal("100.00");
    private final Product DEFAULT_PRODUCT = new Product(DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_PRICE);
    private final ProductMapper mapper = new ProductMapper();

    @Test
    void shouldConvertProductToProductDTO() {
        ProductDTO product = mapper.map(DEFAULT_PRODUCT);

        assertEquals(DEFAULT_PRODUCT.getId(), product.getId());
        assertEquals(DEFAULT_NAME, product.getName());
        assertEquals(DEFAULT_DESCRIPTION, product.getDescription());
        assertEquals(DEFAULT_PRICE, product.getPrice());
    }

}
