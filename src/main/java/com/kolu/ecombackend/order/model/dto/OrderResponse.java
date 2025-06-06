package com.kolu.ecombackend.order.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kolu.ecombackend.order.model.Order;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponse(
        Integer id,
        String orderNumber,
        Order.OrderStatus status,
        Integer userId,
        String userEmail,
        List<OrderItemResponse> orderItems,
        BigDecimal subtotal,
        BigDecimal tax,
        BigDecimal shippingCost,
        BigDecimal total,
        ShippingAddressResponse shippingAddress,
        Order.PaymentMethod paymentMethod,
        Order.PaymentStatus paymentStatus,
        String paymentTransactionId,
        String notes,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime shippedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime deliveredAt
) {}
