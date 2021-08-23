package com.compassouol.productms.core.mappers;

import com.compassouol.productms.commons.infrastructure.Mapper;
import com.compassouol.productms.core.domain.Product;
import com.compassouol.productms.core.ports.dto.ProductFormDTO;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
public class ProductFormMapper implements Mapper<ProductFormDTO, Product> {

    @Override
    public Product map(ProductFormDTO source) {
        return new Product(source.getName(), source.getDescription(), source.getPrice().setScale(2, RoundingMode.HALF_DOWN));
    }

}
