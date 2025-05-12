package com.kolu.ecombackend.users.service;

import com.kolu.ecombackend.auth.model.Roles;
import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.auth.repository.UserRepository;
import com.kolu.ecombackend.users.model.AddressResponse;
import com.kolu.ecombackend.users.model.ChangePasswordRequest;
import com.kolu.ecombackend.users.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userToResponse(user);
    }

    public UserResponse getCurrentUser() {
        var user = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userToResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        var users = userRepository.findAllByRole(Roles.USER).orElseThrow(() -> new UsernameNotFoundException("No users found"));

        return users.stream().map(this::userToResponse).toList();
    }

    public boolean changePassword(ChangePasswordRequest request) {
        var user = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        return true;
    }

    private UserResponse userToResponse(User user) {
        var address = AddressResponse.builder()
                .id(user.getAddress().getId())
                .division(user.getAddress().getDivision())
                .district(user.getAddress().getDistrict())
                .postOffice(user.getAddress().getPostOffice())
                .policeStation(user.getAddress().getPoliceStation())
                .landmark(user.getAddress().getLandmark())
                .build();

        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .address(address)
                .role(user.getRole())
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
