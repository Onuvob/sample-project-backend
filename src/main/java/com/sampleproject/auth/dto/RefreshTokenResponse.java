package com.sampleproject.auth.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
