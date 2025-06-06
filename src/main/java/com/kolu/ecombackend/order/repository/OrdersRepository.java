package com.kolu.ecombackend.order.repository;

import com.kolu.ecombackend.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
    
    List<Order> findByUserIdOrderByCreatedAtDesc(Integer userId);
    
    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);
    
    List<Order> findByOrderNumber(String orderNumber);
}
