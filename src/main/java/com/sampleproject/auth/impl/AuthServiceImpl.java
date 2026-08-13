package com.sampleproject.auth.impl;

import com.sampleproject.auth.dto.LoginRequest;
import com.sampleproject.auth.dto.LoginResponse;
import com.sampleproject.auth.dto.RegisterRequest;
import com.sampleproject.user.dto.UserResponse;
import com.sampleproject.auth.entity.RefreshToken;
import com.sampleproject.user.entity.User;
import com.sampleproject.common.enums.Role;
import com.sampleproject.user.repository.UserRepository;
import com.sampleproject.auth.service.AuthService;
import com.sampleproject.auth.service.RefreshTokenService;
import com.sampleproject.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.access-token-expiration}")
    private long accessExpiration;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .role(Role.OWNER)
                .build();
        userRepository.save(user);
    }
    @Override
    public LoginResponse login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        UserResponse userResponse =
                UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .build();

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(accessExpiration)
                .user(userResponse)
                .build();
    }

}
