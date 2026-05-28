package com.smartcart.service;

import com.smartcart.dto.CartDTO;
import com.smartcart.dto.CartRequestDTO;
import com.smartcart.entity.Cart;
import com.smartcart.entity.Product;
import com.smartcart.entity.User;
import com.smartcart.repository.CartRepository;
import com.smartcart.repository.ProductRepository;
import com.smartcart.repository.UserRepository;
import com.smartcart.security.SecurityUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Add item to logged-in user's cart
    public CartDTO addToCart(CartRequestDTO dto) {

        String email =
                SecurityUtils.getCurrentUserEmail();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        Product product =
                productRepository.findById(dto.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException("Product not found"));

        Cart cart = new Cart();

        // IMPORTANT
        cart.setUser(user);

        cart.setProduct(product);
        cart.setQuantity(dto.getQuantity());

        Cart saved = repository.save(cart);

        return modelMapper.map(saved, CartDTO.class);
    }

    public List<Cart> getCartByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public void removeFromCart(Long id) {
        repository.deleteById(id);
    }

    // Logged-in user's cart
    public List<CartDTO> getMyCart() {

        String email =
                SecurityUtils.getCurrentUserEmail();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        return repository.findByUserId(user.getId())
                .stream()
                .map(cart ->
                        modelMapper.map(cart, CartDTO.class))
                .toList();
    }
}