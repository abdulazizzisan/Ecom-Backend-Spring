package com.kolu.ecombackend.auth.service;

import com.kolu.ecombackend.auth.model.Address;
import com.kolu.ecombackend.auth.model.Roles;
import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.auth.model.dto.LoginRequest;
import com.kolu.ecombackend.auth.model.dto.LoginResponse;
import com.kolu.ecombackend.auth.model.dto.RegisterRequest;
import com.kolu.ecombackend.auth.model.dto.RegisterResponse;
import com.kolu.ecombackend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse loginUser(LoginRequest loginRequest, Roles role) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRole().equals(role)) {
            throw new IllegalArgumentException("User not authorized");
        }

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        String token = jwtService.generateToken(user);
        Date expiration = jwtService.extractExpiration(token);

        return LoginResponse.builder()
                .success(true)
                .role(role)
                .token(token)
                .validTill(expiration)
                .build();
    }

    public RegisterResponse registerUser(RegisterRequest request, Roles role) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("User Already Exists");
        }
        var a = request.address();
        Address address = Address.builder()
                .division(a.division())
                .district(a.district())
                .postOffice(a.postOffice())
                .policeStation(a.policeStation())
                .landmark(a.landmark())
                .build();

        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .address(address)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        Date expiration = jwtService.extractExpiration(token);

        return RegisterResponse.builder()
                .token(token)
                .validTill(expiration)
                .registeredUser(request)
                .role(user.getRole())
                .build();
    }
}
