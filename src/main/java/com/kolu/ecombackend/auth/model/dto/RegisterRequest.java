package com.kolu.ecombackend.auth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequest(
        String firstname,
        String lastname,
        AddressRequest address,
        @Pattern(regexp = "^\\+8801\\d{9}$", message = "Enter a valid Bangladeshi phone number")
        String phoneNumber,
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long"
        )
        String password
) { }
