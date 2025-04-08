package com.kolu.ecombackend.auth.controller;

import com.kolu.ecombackend.auth.model.Roles;
import com.kolu.ecombackend.auth.model.dto.AuthResponse;
import com.kolu.ecombackend.auth.model.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Authentication")
@RestController
@RequestMapping("/api/v1/auth/user")
public class UserController {
    @Operation(
            summary = "Register a new user",
            description = """
                    -> Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.\n
                    -> Phone number must be a valid Bangladeshi phone number starting with +8801 and followed by 9 digits.
                    """
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(
            @RequestBody
            RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(new AuthResponse(
                true,
                Roles.USER.name(),
                "token",
                "email email"
        ));
    }
}
