package com.kolu.ecombackend.order.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedOrderResponse(
        List<OrderResponse> content,
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages,
        Boolean lastPage
) {}
