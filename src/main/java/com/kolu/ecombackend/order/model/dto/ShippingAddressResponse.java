package com.kolu.ecombackend.order.model.dto;

import lombok.Builder;

@Builder
public record ShippingAddressResponse(
        String division,
        String district,
        String postOffice,
        String policeStation,
        String landmark,
        String note
) {}
