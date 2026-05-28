package com.smartcart.controller;

import com.smartcart.dto.ProductDTO;
import com.smartcart.entity.Product;
import com.smartcart.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    @Autowired
    private ProductService service;

    // CREATE PRODUCT

    @Operation(summary = "Add new product")
    @PostMapping
    public ProductDTO saveProduct(
            @Valid @RequestBody ProductDTO dto) {

        return service.saveProduct(dto);
    }

    // GET ALL PRODUCTS

    @Operation(summary = "Get all products")
    @GetMapping
    public List<ProductDTO> getAllProducts() {

        return service.getAllProducts();
    }

    // GET PRODUCT BY ID

    @Operation(summary = "Get product by ID")
    @GetMapping("/{id:[0-9]+}")
    public ProductDTO getProductById(
            @PathVariable Long id) {

        return service.getProductById(id);
    }

    // UPDATE PRODUCT

    @Operation(summary = "Update existing product")
    @PutMapping("/{id}")
    public ProductDTO updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto) {

        return service.updateProduct(id, dto);
    }

    // DELETE PRODUCT

    @Operation(summary = "Delete product")
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        service.deleteProduct(id);

        return "Product deleted successfully";
    }

    // PAGINATION

    @Operation(summary = "Get paginated products")
    @GetMapping("/paged")
    public Page<Product> getProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return service.getProducts(page, size);
    }

    // SORT ASC

    @Operation(summary = "Sort products ascending")
    @GetMapping("/sort")
    public List<Product> getProductsSorted(
            @RequestParam String field) {

        return service.getProductsSorted(field);
    }

    // SORT DESC

    @Operation(summary = "Sort products descending")
    @GetMapping("/sort-desc")
    public List<Product> getProductsSortedDesc(
            @RequestParam String field) {

        return service.getProductsSortedDesc(field);
    }

    // SEARCH

    @Operation(summary = "Search products by name")
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String name) {

        return service.searchProducts(name);
    }
}