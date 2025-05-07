package com.kolu.ecombackend.auth.service;

import com.kolu.ecombackend.auth.model.Roles;
import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.auth.model.dto.UserInfo;
import com.kolu.ecombackend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final UserRepository userRepository;


    public UserInfo getUserInfo(Roles role) {
        User user = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getRole() != role) {
            throw new IllegalArgumentException("User not authorized");
        }

        return UserInfo.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .role(user.getRole().name())
                .build();
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Typically, the email or username is stored here
        }
        throw new IllegalStateException("No authenticated user found");
    }
}
