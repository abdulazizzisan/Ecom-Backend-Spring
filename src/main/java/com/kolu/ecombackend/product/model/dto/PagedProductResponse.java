package com.kolu.ecombackend.product.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedProductResponse(
        List<ProductResponse> content,
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages,
        Boolean lastPage
) {
}
