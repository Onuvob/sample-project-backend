package com.sampleproject.auth.controller;

import com.sampleproject.auth.dto.*;
import com.sampleproject.auth.entity.RefreshToken;
import com.sampleproject.auth.repository.RefreshTokenRepository;
import com.sampleproject.auth.service.AuthService;
import com.sampleproject.auth.service.PasswordResetService;
import com.sampleproject.auth.service.RefreshTokenService;
import com.sampleproject.util.ApiResponse;
import com.sampleproject.util.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${jwt.refresh-token-expiration}")
    private long refreshExpiration;
    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Registration successful", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request){

        RefreshToken refreshToken = refreshTokenRepository
                        .findByToken(request.getRefreshToken())
                        .map(refreshTokenService::verifyExpiration)
                        .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        String accessToken = jwtService.generateToken(refreshToken.getUser());

        RefreshTokenResponse response = RefreshTokenResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getToken())
                        .expiresIn(900000L) //refreshExpiration / 1000
                        .build();

        return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
        passwordResetService.createPasswordResetToken(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Password reset link generated", null));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        passwordResetService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password reset successful", null));
    }
}
