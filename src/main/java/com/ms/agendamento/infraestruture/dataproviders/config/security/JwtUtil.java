package com.ms.agendamento.infraestruture.dataproviders.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtUtil {

    //@Value("${jwt.secret}")
    private static final String SECRET_KEY = "DbqQrnCVpwdEsoU7YVUxiAOG1vajrqdp33jFk5abEFHTM8A7pyJzFWfrApt42gW8";

    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static Long extractUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return Long.valueOf(claims.get("userId").toString());
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Token inválido ou expirado", e);
        }
    }
}