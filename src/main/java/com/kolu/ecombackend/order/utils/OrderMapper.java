package com.kolu.ecombackend.order.utils;

import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.order.model.Order;
import com.kolu.ecombackend.order.model.OrderItem;
import com.kolu.ecombackend.order.model.dto.*;
import com.kolu.ecombackend.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .userId(order.getUser().getId())
                .userEmail(order.getUser().getEmail())
                .orderItems(order.getOrderItems().stream()
                        .map(this::toOrderItemResponse)
                        .toList())
                .subtotal(order.getSubtotal())
                .tax(order.getTax())
                .shippingCost(order.getShippingCost())
                .total(order.getTotal())
                .shippingAddress(toShippingAddressResponse(order))
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .paymentTransactionId(order.getPaymentTransactionId())
                .notes(order.getNotes())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .shippedAt(order.getShippedAt())
                .deliveredAt(order.getDeliveredAt())
                .build();
    }

    public Order toEntity(OrderRequest request, User user, List<Product> products) {
        String orderNumber = generateOrderNumber();
        
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .status(Order.OrderStatus.PENDING)
                .user(user)
                .tax(request.tax() != null ? request.tax() : BigDecimal.ZERO)
                .shippingCost(request.shippingCost() != null ? request.shippingCost() : BigDecimal.ZERO)
                .shippingDivision(request.shippingAddress().division())
                .shippingDistrict(request.shippingAddress().district())
                .shippingPostOffice(request.shippingAddress().postOffice())
                .shippingPoliceStation(request.shippingAddress().policeStation())
                .shippingLandmark(request.shippingAddress().landmark())
                .shippingNote(request.shippingAddress().note())
                .paymentMethod(request.paymentMethod())
                .paymentStatus(Order.PaymentStatus.PENDING)
                .notes(request.notes())
                .build();

        List<OrderItem> orderItems = request.orderItems().stream()
                .map(itemRequest -> {
                    Product product = products.stream()
                            .filter(p -> p.getId().equals(itemRequest.productId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.productId()));
                    
                    BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));
                    
                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(itemRequest.quantity())
                            .unitPrice(product.getPrice())
                            .totalPrice(totalPrice)
                            .productName(product.getName())
                            .productDescription(product.getDescription())
                            .build();
                })
                .toList();

        order.setOrderItems(orderItems);
        
        // Calculate totals
        BigDecimal subtotal = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        order.setSubtotal(subtotal);
        order.setTotal(subtotal.add(order.getTax()).add(order.getShippingCost()));
        
        return order;
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProductName())
                .productDescription(orderItem.getProductDescription())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    public ShippingAddressResponse toShippingAddressResponse(Order order) {
        return ShippingAddressResponse.builder()
                .division(order.getShippingDivision())
                .district(order.getShippingDistrict())
                .postOffice(order.getShippingPostOffice())
                .policeStation(order.getShippingPoliceStation())
                .landmark(order.getShippingLandmark())
                .note(order.getShippingNote())
                .build();
    }

    public void updateOrderStatus(Order order, OrderStatusUpdateRequest request) {
        order.setStatus(request.status());
        
        if (request.paymentTransactionId() != null) {
            order.setPaymentTransactionId(request.paymentTransactionId());
            order.setPaymentStatus(Order.PaymentStatus.PAID);
        }
        
        // Set timestamps based on status
        switch (request.status()) {
            case SHIPPED -> order.setShippedAt(LocalDateTime.now());
            case DELIVERED -> {
                if (order.getShippedAt() == null) {
                    order.setShippedAt(LocalDateTime.now());
                }
                order.setDeliveredAt(LocalDateTime.now());
            }
        }
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + 
               UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
