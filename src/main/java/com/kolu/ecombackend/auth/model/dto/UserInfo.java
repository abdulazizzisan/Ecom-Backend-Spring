package com.kolu.ecombackend.auth.model.dto;

import com.kolu.ecombackend.auth.model.Address;
import lombok.Builder;

@Builder
public record UserInfo (
    String firstname,
    String lastname,
    String phoneNumber,
    String email,
    Address address,
    String role
){}
