package com.compassouol.productms.core.mappers;

import com.compassouol.productms.core.ports.dto.ProductFormDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductFormMapperTest {

    private static final String INPUT_NAME = "Product";
    private static final String INPUT_DESCRIPTION = "Description Product";
    private static final BigDecimal INPUT_PRICE = new BigDecimal("100.00");
    private static final ProductFormDTO form = new ProductFormDTO(INPUT_NAME, INPUT_DESCRIPTION, INPUT_PRICE);
    private static final ProductFormMapper mapper = new ProductFormMapper();

    @Test
    void shouldConvertProductFormDTOToProduct() {
        var product = mapper.map(form);

        assertEquals(INPUT_NAME, product.getName());
        assertEquals(INPUT_DESCRIPTION, product.getDescription());
        assertEquals(INPUT_PRICE, product.getPrice());
    }

}
