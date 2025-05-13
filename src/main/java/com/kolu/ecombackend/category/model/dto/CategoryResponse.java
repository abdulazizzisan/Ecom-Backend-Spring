package com.kolu.ecombackend.category.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        String imageUrl,
        int productCount,
        LocalDateTime createdAt
) {
}
