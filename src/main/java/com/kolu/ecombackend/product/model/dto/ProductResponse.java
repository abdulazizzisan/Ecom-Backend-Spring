package com.kolu.ecombackend.product.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        Integer id,
        @NotBlank
        String name,
        String description,
        @NotNull @Positive
        BigDecimal price,
        @NotNull @PositiveOrZero
        Integer stockQuantity,
        @NotNull
        Integer categoryId
) {
}
