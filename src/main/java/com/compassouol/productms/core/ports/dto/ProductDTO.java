package com.compassouol.productms.core.ports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

}
