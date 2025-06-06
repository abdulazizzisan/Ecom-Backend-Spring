package com.kolu.ecombackend.order.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ShippingAddressRequest(
        @NotBlank(message = "Division is required")
        String division,
        
        @NotBlank(message = "District is required")
        String district,
        
        String postOffice,
        
        String policeStation,
        
        @NotBlank(message = "Landmark is required")
        String landmark,
        
        String note
) {}
