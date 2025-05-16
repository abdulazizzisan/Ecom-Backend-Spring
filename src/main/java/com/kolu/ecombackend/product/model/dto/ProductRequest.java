package com.kolu.ecombackend.product.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(
        @NotBlank
        String name,
        String description,
        @NotBlank
        BigDecimal price,
        @NotBlank
        Integer stockQuantity,
        @NotBlank
        Integer categoryId
) {
}
