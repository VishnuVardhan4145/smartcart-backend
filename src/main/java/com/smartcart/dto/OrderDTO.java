package com.smartcart.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private Long id;

    private Long userId;

    private Double totalAmount;

    private String status;
}