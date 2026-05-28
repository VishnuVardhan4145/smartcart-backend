package com.smartcart.service;

import com.smartcart.dto.ProductDTO;
import com.smartcart.entity.Product;
import com.smartcart.exception.ProductNotFoundException;
import com.smartcart.exception.ResourceNotFoundException;
import com.smartcart.repository.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    // Save Product

    public ProductDTO saveProduct(ProductDTO dto) {

        Product product =
                modelMapper.map(dto, Product.class);

        Product saved =
                repository.save(product);

        return modelMapper.map(saved, ProductDTO.class);
    }

    // Get All Products

    public List<ProductDTO> getAllProducts() {

        return repository.findAll()
                .stream()
                .map(product ->
                        modelMapper.map(
                                product,
                                ProductDTO.class))
                .collect(Collectors.toList());
    }

    // Get Product By Id

    public ProductDTO getProductById(Long id) {

        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found with id: " + id));

        return modelMapper.map(
                product,
                ProductDTO.class);
    }

    // Update Product

    public ProductDTO updateProduct(
            Long id,
            ProductDTO dto) {

        Product existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found with id: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setStock(dto.getStock());

        Product updated =
                repository.save(existing);

        return modelMapper.map(
                updated,
                ProductDTO.class);
    }

    // Delete Product

    public void deleteProduct(Long id) {

        Product existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found with id: " + id));

        repository.delete(existing);
    }

    // Pagination

    public Page<Product> getProducts(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return repository.findAll(pageable);
    }

    // Sorting Asc

    public List<Product> getProductsSorted(
            String field) {

        return repository.findAll(
                Sort.by(field));
    }

    // Sorting Desc

    public List<Product> getProductsSortedDesc(
            String field) {

        return repository.findAll(
                Sort.by(
                        Sort.Direction.DESC,
                        field));
    }

    // Search

    public List<Product> searchProducts(
            String name) {

        return repository
                .findByNameContainingIgnoreCase(name);
    }
}