package com.kolu.ecombackend.users.model;

import com.kolu.ecombackend.auth.model.Address;
import com.kolu.ecombackend.auth.model.Roles;
import lombok.Builder;

@Builder
public record UserResponse(
        Integer id,
        String firstname,
        String lastname,
        String phoneNumber,
        String email,
        AddressResponse address,
        Roles role
) {
}
