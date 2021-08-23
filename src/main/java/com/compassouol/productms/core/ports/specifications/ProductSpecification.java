package com.compassouol.productms.core.ports.specifications;

import com.compassouol.productms.core.domain.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> getProductsBy(String q, BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, builder) -> {
            List<Predicate> filters = new ArrayList<>();
            if (q != null) {
                List<Predicate> filter = new ArrayList<>();
                filter.add(builder.equal(builder.lower(root.get("name")), q.toLowerCase()));
                filter.add(builder.equal(builder.lower(root.get("description")), q.toLowerCase()));
                filters.add(builder.or(filter.toArray(new Predicate[0])));
            }
            if (minPrice != null) {
                filters.add(builder.ge(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                filters.add(builder.le(root.get("price"), maxPrice));
            }
            return builder.and(filters.toArray(new Predicate[0]));
        };
    }

}
