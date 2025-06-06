package com.kolu.ecombackend.order.model.dto;

import com.kolu.ecombackend.order.model.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderStatusUpdateRequest(
        @NotNull(message = "Status is required")
        Order.OrderStatus status,
        
        String paymentTransactionId
) {}
