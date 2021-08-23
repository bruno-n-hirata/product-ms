package com.compassouol.productms.core;

import com.compassouol.productms.commons.exceptions.ResourceNotFoundException;
import com.compassouol.productms.core.domain.Product;
import com.compassouol.productms.core.mappers.ProductFormMapper;
import com.compassouol.productms.core.mappers.ProductMapper;
import com.compassouol.productms.core.ports.ProductRepository;
import com.compassouol.productms.core.ports.dto.ProductDTO;
import com.compassouol.productms.core.ports.dto.ProductFormDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final Long productId = 1L;

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Spy
    private ProductMapper mapper;

    @Spy
    private ProductFormMapper formMapper;

    @Test
    public void listAllProductsShouldReturnAListWithAllProducts() {
        var product1 = new Product("Product 1", "Description 1", new BigDecimal("100.00"));
        var product2 = new Product("Product 2", "Description 2", new BigDecimal("100.00"));
        var products = List.of(product1, product2);

        when(repository.findAll()).thenReturn(products);

        var productDTOList = service.listAll();

        then(repository).should(only()).findAll();
        then(mapper).should(times(2)).map(any(Product.class));

        assertEquals(2, productDTOList.size());

        for (int i = 0; i < productDTOList.size(); i++) {
            assertEquals(products.get(i).getId(), productDTOList.get(i).getId());
            assertEquals(products.get(i).getName(), productDTOList.get(i).getName());
            assertEquals(products.get(i).getDescription(), productDTOList.get(i).getDescription());
            assertEquals(products.get(i).getPrice(), productDTOList.get(i).getPrice());
        }
    }

    @Test
    void listAllProductsShouldReturnAnEmptyList() {
        given(repository.findAll()).willReturn(Collections.emptyList());

        var productDTOList = service.listAll();

        then(repository).should(only()).findAll();
        then(mapper).should(times(0)).map(any(Product.class));

        assertEquals(0, productDTOList.size());
    }

    @Test
    public void shouldReturnProduct() {
        var product = new Product("Product", "Description", new BigDecimal("100.00"));

        when(repository.findById(productId))
                .thenReturn(Optional.of(product));

        var actualProduct = service.getById(productId);

        assertNotNull(actualProduct);

        var expectedProduct = new ProductDTO(null, "Product", "Description", new BigDecimal("100.00"));
        assertEquals(actualProduct.getId(), expectedProduct.getId());
        assertEquals(actualProduct.getName(), expectedProduct.getName());
        assertEquals(actualProduct.getDescription(), expectedProduct.getDescription());
        assertEquals(actualProduct.getPrice(), expectedProduct.getPrice());
    }

    @Test
    public void shouldThrowExceptionWhenProductIdNotExistingInRepository() {
        given(repository.findById(productId)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getById(productId)
        );

        then(repository).should(only()).findById(productId);
    }

    @Test
    public void shouldCreateAProduct() {
        var productForm = new ProductFormDTO("Product", "Description", new BigDecimal("100.00"));
        var productDTO = new ProductDTO(productId, "Product", "Description", new BigDecimal("100.00"));
        var product = new Product(productId, "Product", "Description", new BigDecimal("100.00"));

        given(formMapper.map(productForm)).willReturn(product);
        given(mapper.map(product)).willReturn(productDTO);

        var createdProduct = service.createNew(productForm);

        then(formMapper).should(only()).map(productForm);
        then(mapper).should(only()).map(product);
        then(repository).should(only()).save(product);

        assertEquals(productDTO, createdProduct);
    }

    @Test
    public void shouldUpdateAProduct() {
        var productForm = new ProductFormDTO("Product", "Description", new BigDecimal("100.00"));
        var productDTO = new ProductDTO(productId, "Product", "Description", new BigDecimal("100.00"));
        var product = new Product(productId, "Product", "Description", new BigDecimal("100.00"));

        given(repository.findById(productId)).willReturn(Optional.of(product));
        given(formMapper.map(productForm)).willReturn(product);
        given(mapper.map(product)).willReturn(productDTO);

        var updatedProduct = service.updateRegister(productId, productForm);

        then(formMapper).should(only()).map(productForm);
        then(mapper).should(only()).map(product);
        then(repository).should().findById(productId);
        then(repository).should().saveAndFlush(product);

        assertEquals(productDTO, updatedProduct);
    }

    @Test
    public void shouldThrowExceptionWhenUpdateAProductNotExistingInRepository() {
        var productForm = new ProductFormDTO("Product", "Description", new BigDecimal("100.00"));
        var product = new Product(productId, "Product", "Description", new BigDecimal("100.00"));

        given(repository.findById(productId)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateRegister(productId, productForm)
        );

        then(repository).should(only()).findById(productId);
        then(formMapper).should(times(0)).map(productForm);
        then(repository).should(times(0)).saveAndFlush(product);
    }

    @Test
    public void shouldDeleteAProduct() {
        var product = new Product(productId, "Product", "Description", new BigDecimal("100.00"));

        given(repository.findById(productId)).willReturn(Optional.of(product));

        service.deleteRegister(productId);

        then(repository).should().findById(productId);
        then(repository).should().deleteById(productId);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteAProductNotExistingInRepository() {
        given(repository.findById(productId)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteRegister(productId)
        );

        then(repository).should(only()).findById(productId);
        then(repository).should(times(0)).deleteById(productId);
    }

}
