package com.kolu.ecombackend.order.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(
        Integer id,
        Integer productId,
        String productName,
        String productDescription,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {}
