package com.sampleproject.auth.impl;

import com.sampleproject.auth.dto.ResetPasswordRequest;
import com.sampleproject.auth.entity.PasswordResetToken;
import com.sampleproject.user.entity.User;
import com.sampleproject.auth.repository.PasswordResetTokenRepository;
import com.sampleproject.user.repository.UserRepository;
import com.sampleproject.auth.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createPasswordResetToken(String email){

        User user = userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        PasswordResetToken token =
                PasswordResetToken.builder()
                        .token(UUID.randomUUID().toString())
                        .user(user)
                        .expiryDate(LocalDateTime.now().plusMinutes(15))
                        .used(false)
                        .build();

        tokenRepository.save(token);

        /*
          Here you can send email.
          Example:
          localhost:8080/reset-password?token=xxxx
        */
    }

    @Override
    public void resetPassword(ResetPasswordRequest request){

        PasswordResetToken resetToken = tokenRepository
                        .findByToken(request.getToken())
                        .orElseThrow(() -> new RuntimeException("Invalid token"));

        if(resetToken.isExpired() || resetToken.isUsed()){
            throw new RuntimeException("Token expired");
        }
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

}
