package com.smartcart.controller;

import com.smartcart.dto.CartDTO;
import com.smartcart.dto.CartRequestDTO;
import com.smartcart.entity.Cart;
import com.smartcart.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService service;

    // Add item to cart
    @PostMapping
    public CartDTO addToCart(@RequestBody CartRequestDTO dto) {
        return service.addToCart(dto);
    }

    @GetMapping("/test")
    public String test() {
        return "Cart Working";
    }

    // Admin/testing endpoint
    @GetMapping("/{userId:[0-9]+}")
    public List<Cart> getCartByUser(@PathVariable Long userId) {
        return service.getCartByUser(userId);
    }

    @DeleteMapping("/{id}")
    public String removeFromCart(@PathVariable Long id) {

        service.removeFromCart(id);

        return "Item removed";
    }

    // Logged-in user's cart
    @GetMapping("/my-cart")
    public List<CartDTO> getMyCart() {
        return service.getMyCart();
    }
}