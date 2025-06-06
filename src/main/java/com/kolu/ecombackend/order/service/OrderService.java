package com.kolu.ecombackend.order.service;

import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.order.model.Order;
import com.kolu.ecombackend.order.model.dto.*;
import com.kolu.ecombackend.order.repository.OrdersRepository;
import com.kolu.ecombackend.order.utils.OrderMapper;
import com.kolu.ecombackend.product.model.Product;
import com.kolu.ecombackend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // This method retrieves the currently authenticated user from the security context
    private User getCurrentUser() {
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User user) {
            return user;
        }
        throw new RuntimeException("User not authenticated");
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = getCurrentUser();
        
        // Validate and get products
        List<Integer> productIds = request.orderItems().stream()
                .map(OrderItemRequest::productId)
                .toList();
        
        List<Product> products = productRepository.findAllById(productIds);
        
        if (products.size() != productIds.size()) {
            throw new RuntimeException("Some products not found");
        }
        
        // Check stock availability
        for (OrderItemRequest itemRequest : request.orderItems()) {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(itemRequest.productId()))
                    .findFirst()
                    .orElseThrow();
            
            if (product.getStockQuantity() < itemRequest.quantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
        }
        
        // Create order
        Order order = orderMapper.toEntity(request, user, products);
        Order savedOrder = ordersRepository.save(order);
        
        // Update product stock
        for (OrderItemRequest itemRequest : request.orderItems()) {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(itemRequest.productId()))
                    .findFirst()
                    .orElseThrow();
            
            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());
            productRepository.save(product);
        }
        
        return orderMapper.toResponse(savedOrder);
    }

    public PagedOrderResponse getAllOrders(int page, int size, Order.OrderStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        Page<Order> ordersPage;
        if (status != null) {
            ordersPage = ordersRepository.findByStatus(status, pageable);
        } else {
            ordersPage = ordersRepository.findAll(pageable);
        }
        
        List<OrderResponse> content = ordersPage.getContent()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
        
        return PagedOrderResponse.builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(ordersPage.getTotalElements())
                .totalPages(ordersPage.getTotalPages())
                .lastPage(ordersPage.isLast())
                .build();
    }

    public OrderResponse getOrderById(Integer orderId) {
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getOrdersByUserId(Integer userId) {
        List<Order> orders = ordersRepository.findByUserIdOrderByCreatedAtDesc(userId);
        
        return orders.stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional
    public OrderResponse updateOrderStatus(Integer orderId, OrderStatusUpdateRequest request) {
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        orderMapper.updateOrderStatus(order, request);
        Order updatedOrder = ordersRepository.save(order);
        
        return orderMapper.toResponse(updatedOrder);
    }

    @Transactional
    public OrderResponse cancelOrder(Integer orderId) {
        User user = getCurrentUser();
        
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Check if user owns the order or is admin
        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().name().equals("ADMIN")) {
            throw new RuntimeException("Not authorized to cancel this order");
        }
        
        // Only allow cancellation for certain statuses
        if (order.getStatus() == Order.OrderStatus.SHIPPED || 
            order.getStatus() == Order.OrderStatus.DELIVERED ||
            order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot cancel order in current status");
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        
        // Restore product stock
        order.getOrderItems().forEach(orderItem -> {
            Product product = orderItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
            productRepository.save(product);
        });
        
        Order updatedOrder = ordersRepository.save(order);
        return orderMapper.toResponse(updatedOrder);
    }

    public void deleteOrder(Integer orderId) {
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        ordersRepository.delete(order);
    }
}
