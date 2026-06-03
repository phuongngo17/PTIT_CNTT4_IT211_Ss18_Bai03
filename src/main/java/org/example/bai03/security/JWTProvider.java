package org.example.bai03.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JWTProvider {

    @Value("${jwt-secret}")
    private String jwtSecret;
    @Value("${jwt-expired}")
    private Long jwtExpired;
    @Value("${jwt-refresh-expired}")
    private Long jwtRefreshExpired;

    public String generateToken(String username) {
        try {
            Date today = new Date();
            Date expired = new Date(today.getTime() + jwtExpired);
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            return Jwts.builder()
                    .subject(username)
                    .issuedAt(today)
                    .expiration(expired)
                    .signWith(key)
                    .compact();

        } catch (Exception e) {
            throw new RuntimeException("Không tạo được JWT", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (UnsupportedJwtException e) {
            log.info("JWT không được hỗ trợ");
            throw new RuntimeException("JWT không được hỗ trợ", e);

        } catch (ExpiredJwtException e) {
            log.info("JWT đã hết hạn");

            throw new RuntimeException("JWT đã hết hạn", e);

        } catch (SignatureException e) {

            log.info("Sai chữ ký JWT");

            throw new RuntimeException("Sai chữ ký JWT", e);

        } catch (IllegalArgumentException e) {

            log.info("JWT rỗng");

            throw new RuntimeException("JWT rỗng", e);

        } catch (JwtException e) {
            log.info("Không xác thực được JWT");
            throw new RuntimeException("Không xác thực được JWT", e);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (Exception e) {

            log.info("Không lấy được username từ JWT");
            throw new RuntimeException("Không lấy được username từ JWT", e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        String username = getUsernameFromToken(token);

        return username.equals(userDetails.getUsername()) && validateToken(token);
    }
}