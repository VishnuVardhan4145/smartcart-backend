package com.smartcart.controller;

import com.smartcart.dto.OrderDTO;
import com.smartcart.dto.OrderRequestDTO;
import com.smartcart.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    // Place order for currently logged-in user
    @PostMapping
    public OrderDTO placeOrder(
            @RequestBody OrderRequestDTO dto) {

        return service.placeOrder(dto);
    }

    // Get current user's orders
    @GetMapping("/my-orders")
    public List<OrderDTO> getMyOrders() {

        return service.getMyOrders();
    }

    @GetMapping("/test")
    public String test() {
        return "Order API Working";
    }
}