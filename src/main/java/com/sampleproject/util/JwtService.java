package com.sampleproject.util;

import com.sampleproject.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long accessExpiration;

    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.access-token-expiration}") long accessExpiration){
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpiration = accessExpiration;
    }

    public String generateToken(User user) {
        // Create a map for extra claims
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("userId", user.getId());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token){

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        }catch(Exception e){
            return false;
        }
    }
}
