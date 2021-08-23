package com.compassouol.productms.core;

import com.compassouol.productms.commons.exceptions.ResourceNotFoundException;
import com.compassouol.productms.core.domain.Product;
import com.compassouol.productms.core.mappers.ProductFormMapper;
import com.compassouol.productms.core.mappers.ProductMapper;
import com.compassouol.productms.core.ports.ProductRepository;
import com.compassouol.productms.core.ports.dto.ProductDTO;
import com.compassouol.productms.core.ports.dto.ProductFormDTO;
import com.compassouol.productms.core.ports.specifications.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper productMapper;
    private final ProductFormMapper productFormMapper;

    public List<ProductDTO> listAll() {
        return repository.findAll().stream().map(productMapper::map).collect(toList());
    }

    public List<ProductDTO> listAllBy(String q, BigDecimal minPrice, BigDecimal maxPrice) {
        return repository.findAll(ProductSpecification.getProductsBy(q, minPrice, maxPrice)).stream().map(productMapper::map).collect(toList());
    }

    public ProductDTO getById(Long id) {
        var product = findProductById(id);
        return productMapper.map(product);
    }

    public ProductDTO createNew(ProductFormDTO form) {
        var product = productFormMapper.map(form);
        repository.save(product);
        return productMapper.map(product);
    }

    public ProductDTO updateRegister(Long id, ProductFormDTO form) {
        var product = findProductById(id);
        var productForm = productFormMapper.map(form);
        BeanUtils.copyProperties(productForm, product, "id");
        repository.saveAndFlush(product);
        return productMapper.map(product);
    }

    public Boolean deleteRegister(Long id) {
        findProductById(id);
        repository.deleteById(id);
        return true;
    }

    private Product findProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find product"));
    }

}
