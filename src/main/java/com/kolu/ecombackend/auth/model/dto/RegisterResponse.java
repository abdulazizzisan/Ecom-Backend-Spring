package com.kolu.ecombackend.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kolu.ecombackend.auth.model.Roles;
import lombok.Builder;

import java.util.Date;
@Builder
public record RegisterResponse(
        String token,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        Date validTill,
        RegisterRequest registeredUser,
        Roles role
) {
}
