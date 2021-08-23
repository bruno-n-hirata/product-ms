package com.compassouol.productms.application;

import com.compassouol.productms.commons.exceptions.ResourceNotFoundException;
import com.compassouol.productms.core.ProductService;
import com.compassouol.productms.core.ports.dto.ProductDTO;
import com.compassouol.productms.core.ports.dto.ProductFormDTO;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    private final Gson gson = new Gson();

    @Test
    public void listAllProductsShouldReturnListOfProducts() throws Exception {
        var product1 = new ProductDTO(1L,"Product 1", "Description 1", new BigDecimal("100.00"));
        var product2 = new ProductDTO(2L,"Product 2", "Description 2", new BigDecimal("100.00"));
        var products = List.of(product1, product2);

        when(service.listAll()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(gson.toJson(products)));
    }

    @Test
    public void listAllProductsShouldReturnAnEmptyList() throws Exception {
        List<ProductDTO> products = Collections.emptyList();

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(gson.toJson(products)));
    }

    @Test
    void shouldReturnProductsWhenSearchByFilters() throws Exception {
        var product = new ProductDTO(1L,"Product", "Description", new BigDecimal("50.00"));
        var products = List.of(product);

        when(service.listAllBy("Product", new BigDecimal("1.00"), new BigDecimal("100.00"))).thenReturn(products);

        mockMvc.perform(get("/products/search")
                        .param("q", "Product")
                        .param("min_price", "1.00")
                        .param("max_price", "100.00"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(gson.toJson(products)));
    }

    @Test
    void shouldReturnEmptyWhenNotFoundProducts() throws Exception {
        List<ProductDTO> products = Collections.emptyList();

        mockMvc.perform(get("/products/search")
                        .param("q", "Product")
                        .param("min_price", "1.00")
                        .param("max_price", "100.00"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(gson.toJson(products)));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        var product = new ProductDTO(1L,"Product", "Description", new BigDecimal("100.00"));

        when(service.getById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Product")))
                .andExpect(jsonPath("$.description", equalTo("Description")))
                .andExpect(jsonPath("$.price", equalTo(100.00)));
    }

    @Test
    void shouldReturnNotFoundForNotFoundProduct() throws Exception {
        when(service.getById(2L)).thenThrow(new ResourceNotFoundException("Cannot find product"));

        mockMvc.perform(get("/products/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpStatus201WhenValidFormIsInformedOnCreate() throws Exception {
        var product = new ProductDTO(1L,"Product", "Description", new BigDecimal("100.00"));
        var productForm = new ProductFormDTO("Product", "Description", new BigDecimal("100.00"));
        var productJson = gson.toJson(productForm);

        when(service.createNew(productForm)).thenReturn(product);

        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Product")))
                .andExpect(jsonPath("$.description", equalTo("Description")))
                .andExpect(jsonPath("$.price", equalTo(100.00)));
    }

    @Test
    public void shouldReturnHttpStatus400WhenInvalidFormIsInformedOnCreate() throws Exception {
        var productForm = new ProductFormDTO("", "", new BigDecimal("0.00"));
        var productJson = gson.toJson(productForm);

        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(productJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status_code", equalTo(400)));
    }

    @Test
    public void shouldReturnHttpStatus200WhenValidFormIsInformedOnUpdate() throws Exception {
        var product = new ProductDTO(1L,"Product", "Description", new BigDecimal("100.00"));
        var productForm = new ProductFormDTO("Product 1", "Description 1", new BigDecimal("200.00"));
        var productJson = gson.toJson(productForm);

        when(service.updateRegister(1L, productForm)).thenReturn(product);

        mockMvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON).content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Product")))
                .andExpect(jsonPath("$.description", equalTo("Description")))
                .andExpect(jsonPath("$.price", equalTo(100.00)));
    }

    @Test
    public void shouldReturnHttpStatus400WhenInvalidFormIsInformedOnUpdate() throws Exception {
        var productForm = new ProductFormDTO("", "", new BigDecimal("0.00"));
        var productJson = gson.toJson(productForm);

        mockMvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON).content(productJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status_code", equalTo(400)));
    }

    @Test
    public void shouldReturnHttpStatus404WhenInvalidIdIsInformedOnUpdate() throws Exception {
        var productForm = new ProductFormDTO("Product", "Description", new BigDecimal("100.00"));
        var productJson = gson.toJson(productForm);

        when(service.updateRegister(2L, productForm)).thenThrow(new ResourceNotFoundException("Cannot find product"));

        mockMvc.perform(put("/products/2").contentType(MediaType.APPLICATION_JSON).content(productJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpStatus200WhenValidIdIsInformedOnDelete() throws Exception {
        when(service.deleteRegister(1L)).thenReturn(true);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnHttpStatus404WhenInvalidIdIsInformedOnDelete() throws Exception {
        when(service.deleteRegister(2L)).thenThrow(new ResourceNotFoundException("Cannot find product"));

        mockMvc.perform(delete("/products/2"))
                .andExpect(status().isNotFound());
    }

}
