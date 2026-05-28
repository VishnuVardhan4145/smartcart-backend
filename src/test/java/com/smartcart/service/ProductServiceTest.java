package com.smartcart.service;

import com.smartcart.dto.ProductDTO;
import com.smartcart.entity.Product;
import com.smartcart.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldSaveProduct() {

        ProductDTO dto = new ProductDTO();
        dto.setName("Laptop");

        Product product = new Product();
        product.setName("Laptop");

        when(modelMapper.map(dto, Product.class))
                .thenReturn(product);

        when(repository.save(product))
                .thenReturn(product);

        when(modelMapper.map(product, ProductDTO.class))
                .thenReturn(dto);

        ProductDTO saved =
                service.saveProduct(dto);

        assertEquals(
                "Laptop",
                saved.getName());
    }
}