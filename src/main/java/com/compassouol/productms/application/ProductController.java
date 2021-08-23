package com.compassouol.productms.application;

import com.compassouol.productms.core.ProductService;
import com.compassouol.productms.core.ports.dto.ProductDTO;
import com.compassouol.productms.core.ports.dto.ProductFormDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductDTO> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/search")
    public List<ProductDTO> search(@RequestParam(name = "q", required = false) String q,
                                   @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
                                   @RequestParam(name = "max_price", required = false) BigDecimal maxPrice) {
        return service.listAllBy(q, minPrice, maxPrice);
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid ProductFormDTO productForm) {
        var product = service.createNew(productForm);
        var uri = URI.create("/products/").resolve(product.getId().toString());
        return created(uri).body(product);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ProductFormDTO productForm) {
        var product = service.updateRegister(id, productForm);
        return ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteRegister(id);
        return ok().build();
    }

}
