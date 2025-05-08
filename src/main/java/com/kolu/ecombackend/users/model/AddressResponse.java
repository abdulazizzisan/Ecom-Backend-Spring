package com.kolu.ecombackend.users.model;

import lombok.Builder;

@Builder
public record AddressResponse(
        Integer id,
        String division,
        String district,
        String postOffice,
        String policeStation,
        String landmark
) {
}
