package com.sampleproject.auth.service;

import com.sampleproject.auth.dto.ResetPasswordRequest;

public interface PasswordResetService {
    void createPasswordResetToken(String email);
    void resetPassword(ResetPasswordRequest request);
}
