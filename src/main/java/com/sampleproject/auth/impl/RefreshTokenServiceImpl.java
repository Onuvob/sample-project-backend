package com.sampleproject.auth.impl;

import com.sampleproject.auth.entity.RefreshToken;
import com.sampleproject.user.entity.User;
import com.sampleproject.auth.repository.RefreshTokenRepository;
import com.sampleproject.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshExpiration;

    @Override
    public RefreshToken createRefreshToken(User user){

        RefreshToken refreshToken =
                repository.findByUser(user)
                        .orElse(
                                RefreshToken.builder()
                                        .user(user)
                                        .build()
                        );

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setExpiryDate(
                LocalDateTime.now()
                        .plusSeconds(refreshExpiration / 1000)
        );

        return repository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token){

        if(token.isExpired()){
            repository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }

    @Override
    public void deleteByUser(User user){
        repository.deleteByUser(user);
    }
}
