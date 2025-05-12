package com.kolu.ecombackend.users.model;


public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
