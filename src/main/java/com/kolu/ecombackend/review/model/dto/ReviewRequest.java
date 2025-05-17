package com.kolu.ecombackend.review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record ReviewRequest(
        String review,
        @Min(0) @Max(5)
        Integer rating
) {
}
