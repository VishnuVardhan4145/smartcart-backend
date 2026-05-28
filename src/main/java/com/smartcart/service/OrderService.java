package com.smartcart.service;

import com.smartcart.dto.OrderDTO;
import com.smartcart.dto.OrderRequestDTO;
import com.smartcart.entity.Order;
import com.smartcart.entity.User;
import com.smartcart.repository.OrderRepository;
import com.smartcart.repository.UserRepository;
import com.smartcart.security.SecurityUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Place order for logged-in user
    public OrderDTO placeOrder(OrderRequestDTO dto) {

        String email =
                SecurityUtils.getCurrentUserEmail();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        Order order = new Order();

        order.setUser(user);      // IMPORTANT
        order.setStatus("PLACED");

        Order saved = repository.save(order);

        return modelMapper.map(saved, OrderDTO.class);
    }

    // Current user's orders
    public List<OrderDTO> getMyOrders() {

        String email =
                SecurityUtils.getCurrentUserEmail();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        return repository.findByUserId(user.getId())
                .stream()
                .map(order ->
                        modelMapper.map(
                                order,
                                OrderDTO.class))
                .toList();
    }
}