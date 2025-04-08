package com.kolu.ecombackend.auth.model.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        boolean success,
        String role,
        String token,
        String email
) {}