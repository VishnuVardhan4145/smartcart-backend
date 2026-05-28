package com.smartcart.dto;

import lombok.Data;

@Data
public class CartDTO {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;
}