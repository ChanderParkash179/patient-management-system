package com.pm.authservice.security.jwt;

import com.pm.authservice.entities.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtService {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret.key}")
    private String SECRET;

    @Value("${jwt.refresh.token.expiration}")
    private Long refreshExpiration;

    public SecretKey getSignInKey() {
        byte[] key = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateToken(User user) {
        log.info("generating token");
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user);
    }

    public String createToken(Map<String, Object> claims, User user) {
        log.info("creating token");
        return buildToken(claims, user, expiration);
    }

    public String buildToken(Map<String, ?> extraClaims, User user, Long expiration) {
        log.info("building token");
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> extraClaims, User user) {
        log.info("generating refresh token");
        return buildToken(extraClaims, user, refreshExpiration);
    }

    public Boolean validateToken(String token) {
        log.info("validating token");
        try {
            Jwts.parser()
                    .verifyWith(this.getSignInKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (SignatureException e) {
            log.error("invalid JWT signature");
            throw new JwtException("invalid JWT signature");
        } catch (Exception e) {
            log.error("invalid JWT");
            throw new JwtException("invalid JWT");
        }
        return Boolean.TRUE;
    }
}