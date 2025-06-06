package com.kolu.ecombackend.order.model.dto;

import com.kolu.ecombackend.order.model.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderRequest(
        @NotEmpty(message = "Order items cannot be empty")
        @Valid
        List<OrderItemRequest> orderItems,
        
        @NotNull(message = "Shipping address is required")
        @Valid
        ShippingAddressRequest shippingAddress,
        
        @NotNull(message = "Payment method is required")
        Order.PaymentMethod paymentMethod,
        
        String notes,
        
        BigDecimal tax,
        
        BigDecimal shippingCost
) {}
