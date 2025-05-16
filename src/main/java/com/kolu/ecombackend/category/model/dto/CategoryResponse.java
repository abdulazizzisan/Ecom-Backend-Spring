package com.kolu.ecombackend.category.model.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        Integer productCount
) {
}
