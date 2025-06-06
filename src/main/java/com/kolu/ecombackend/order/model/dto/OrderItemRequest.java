package com.kolu.ecombackend.order.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderItemRequest(
        @NotNull(message = "Product ID is required")
        Integer productId,
        
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {}
