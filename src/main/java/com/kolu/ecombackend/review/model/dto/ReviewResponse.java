package com.kolu.ecombackend.review.model.dto;

import lombok.Builder;

@Builder
public record ReviewResponse(
        Integer id,
        String review,
        Integer rating
) {}
