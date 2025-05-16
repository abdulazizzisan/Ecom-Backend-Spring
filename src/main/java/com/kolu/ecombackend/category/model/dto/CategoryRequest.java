package com.kolu.ecombackend.category.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoryRequest(
        @NotBlank
        String name,
        String description
) {}

