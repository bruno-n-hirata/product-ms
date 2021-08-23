package com.compassouol.productms.core.mappers;

import com.compassouol.productms.commons.infrastructure.Mapper;
import com.compassouol.productms.core.domain.Product;
import com.compassouol.productms.core.ports.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductDTO> {

    @Override
    public ProductDTO map(Product source) {
        return new ProductDTO(source.getId(), source.getName(), source.getDescription(), source.getPrice());
    }

}
