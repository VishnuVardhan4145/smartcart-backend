package com.smartcart.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY =
            "c21hcnRjYXJ0c2VjcmV0a2V5MTIzNDU2Nzg5MDEyMzQ1Njc4OTA=";

    private static SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(
        String email,
        String role) {

    return Jwts.builder()
            .subject(email)

            .claim("role", role)
            .issuedAt(new Date())

            .expiration(
                    new Date(
                            System.currentTimeMillis()
                                    + 1000 * 60 * 15
                    )
            )

            .signWith(getSignKey())

            .compact();
    }

    public static String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public static boolean validateToken(String token) {

    try {

        Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token);

        return true;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}
    public static String extractRole(String token) {

            return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public static String generateRefreshToken(
        String email) {

    return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(
                    new Date(
                            System.currentTimeMillis()
                                    + 7L * 24 * 60 * 60 * 1000
                    )
            )
            .signWith(getSignKey())
            .compact();
}
}