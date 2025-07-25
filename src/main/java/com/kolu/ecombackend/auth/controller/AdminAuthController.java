package com.kolu.ecombackend.auth.controller;

import com.kolu.ecombackend.auth.model.Roles;
import com.kolu.ecombackend.auth.model.dto.LoginRequest;
import com.kolu.ecombackend.auth.model.dto.LoginResponse;
import com.kolu.ecombackend.auth.model.dto.RegisterRequest;
import com.kolu.ecombackend.auth.model.dto.UserInfo;
import com.kolu.ecombackend.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin Authentication")
@RestController
@RequestMapping("/api/v1/auth/admin")
@RequiredArgsConstructor
public class AdminAuthController {
    private final AuthService authService;

    @Operation(
            summary = "Login as Admin.",
            description = """
                    -> Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.\n
                    -> Phone number must be a valid Bangladeshi phone number starting with +8801 and followed by 9 digits.
                    """
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @RequestBody
            LoginRequest loginRequest
    ) {

        return ResponseEntity.ok(authService.loginUser(loginRequest, Roles.ADMIN));
    }
    @Operation(
            summary = "Register as Admin(Only admin can register admin).",
            description = """
                    -> Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.\n
                    -> Phone number must be a valid Bangladeshi phone number starting with +8801 and followed by 9 digits.
                    """
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody
            RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(authService.registerUser(registerRequest, Roles.ADMIN));
    }
}
