package com.kolu.ecombackend.users.controller;

import com.kolu.ecombackend.users.model.UserResponse;
import com.kolu.ecombackend.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User Management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Get current logged in user",
            description = "Fetch the details of the currently logged in user."
    )
    @GetMapping("/current")
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }

    @Operation(
            summary = "Get User by Email (Admin Only)",
            description = "Fetch user details using email."
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @Operation(
            summary = "Get All User(Admin Only)",
            description = "Fetch all user details."
    )
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getAllUsers() {
        var users = userService.getAllUsers();
        System.out.println(users);
        return users;
    }

}
