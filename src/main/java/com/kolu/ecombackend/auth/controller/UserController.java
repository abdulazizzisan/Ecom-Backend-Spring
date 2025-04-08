package com.kolu.ecombackend.auth.controller;

import com.kolu.ecombackend.auth.model.Roles;
import com.kolu.ecombackend.auth.model.dto.AuthResponse;
import com.kolu.ecombackend.auth.model.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/user")
public class UserController {
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
