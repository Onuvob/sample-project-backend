package com.sampleproject.auth.service;

import com.sampleproject.auth.entity.RefreshToken;
import com.sampleproject.user.entity.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByUser(User user);
}
