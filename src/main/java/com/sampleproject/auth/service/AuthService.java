package com.sampleproject.auth.service;

import com.sampleproject.auth.dto.LoginRequest;
import com.sampleproject.auth.dto.LoginResponse;
import com.sampleproject.auth.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
