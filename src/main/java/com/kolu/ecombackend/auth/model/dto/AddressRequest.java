package com.kolu.ecombackend.auth.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank(message = "Division is required")
        String division,
        @NotBlank(message = "District is required")
        String district,
        String postOffice,
        String policeStation,
        @NotBlank(message = "Landmark is required")
        String landmark
) {
}
