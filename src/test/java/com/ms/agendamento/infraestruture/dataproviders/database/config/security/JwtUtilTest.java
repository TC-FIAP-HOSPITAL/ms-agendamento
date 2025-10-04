//package com.ms.agendamento.infraestruture.dataproviders.database.config.security;
//
//import com.ms.agendamento.infraestruture.dataproviders.config.security.JwtUtil;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class JwtUtilTest {
//
//    private JwtUtil jwtUtil;
//    private static final String SECRET_KEY = "DbqQrnCVpwdEsoU7YVUxiAOG1vajrqdp33jFk5abEFHTM8A7pyJzFWfrApt42gW8";
//
//    @BeforeEach
//    void setUp() {
//        jwtUtil = new JwtUtil();
//    }
//
//    private Key getSigningKey() {
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
//    }
//
//    private String createValidToken(Long userId) {
//        return Jwts.builder()
//                .claim("userId", userId)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
//                .signWith(getSigningKey())
//                .compact();
//    }
//
//    private String createExpiredToken(Long userId) {
//        return Jwts.builder()
//                .claim("userId", userId)
//                .setIssuedAt(new Date(System.currentTimeMillis() - 86400000)) // 24 hours ago
//                .setExpiration(new Date(System.currentTimeMillis() - 3600000)) // 1 hour ago
//                .signWith(getSigningKey())
//                .compact();
//    }
//
//    @Test
//    void testIsTokenValid_WithValidToken_ShouldReturnTrue() {
//        String validToken = createValidToken(123L);
//
//        boolean result = jwtUtil.isTokenValid(validToken);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testIsTokenValid_WithInvalidToken_ShouldReturnFalse() {
//        String invalidToken = "invalid.token.here";
//
//        boolean result = jwtUtil.isTokenValid(invalidToken);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testIsTokenValid_WithExpiredToken_ShouldReturnFalse() {
//        String expiredToken = createExpiredToken(123L);
//
//        boolean result = jwtUtil.isTokenValid(expiredToken);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testIsTokenValid_WithNullToken_ShouldReturnFalse() {
//        boolean result = jwtUtil.isTokenValid(null);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testIsTokenValid_WithEmptyToken_ShouldReturnFalse() {
//        boolean result = jwtUtil.isTokenValid("");
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testIsTokenValid_WithMalformedToken_ShouldReturnFalse() {
//        String malformedToken = "malformed";
//
//        boolean result = jwtUtil.isTokenValid(malformedToken);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testExtractUserId_WithValidToken_ShouldReturnUserId() {
//        Long expectedUserId = 123L;
//        String validToken = createValidToken(expectedUserId);
//
//        Long result = JwtUtil.extractUserId(validToken);
//
//        assertEquals(expectedUserId, result);
//    }
//
//    @Test
//    void testExtractUserId_WithInvalidToken_ShouldThrowRuntimeException() {
//        String invalidToken = "invalid.token.here";
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            JwtUtil.extractUserId(invalidToken);
//        });
//
//        assertEquals("Token inválido ou expirado", exception.getMessage());
//    }
//
//    @Test
//    void testExtractUserId_WithExpiredToken_ShouldThrowRuntimeException() {
//        String expiredToken = createExpiredToken(123L);
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            JwtUtil.extractUserId(expiredToken);
//        });
//
//        assertEquals("Token inválido ou expirado", exception.getMessage());
//    }
//
//    @Test
//    void testExtractUserId_WithNullToken_ShouldThrowRuntimeException() {
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            JwtUtil.extractUserId(null);
//        });
//
//        assertEquals("Token inválido ou expirado", exception.getMessage());
//    }
//
//    @Test
//    void testExtractUserId_WithEmptyToken_ShouldThrowRuntimeException() {
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            JwtUtil.extractUserId("");
//        });
//
//        assertEquals("Token inválido ou expirado", exception.getMessage());
//    }
//
//    @Test
//    void testExtractUserId_WithTokenWithoutUserId_ShouldThrowRuntimeException() {
//        String tokenWithoutUserId = Jwts.builder()
//                .claim("otherClaim", "value")
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
//                .signWith(getSigningKey())
//                .compact();
//
//        assertThrows(RuntimeException.class, () -> {
//            JwtUtil.extractUserId(tokenWithoutUserId);
//        });
//
//    }
//
//    @Test
//    void testExtractUserId_WithDifferentUserIds_ShouldReturnCorrectValues() {
//        Long[] userIds = {1L, 999L, 12345L, 0L};
//
//        for (Long userId : userIds) {
//            String token = createValidToken(userId);
//
//            Long result = JwtUtil.extractUserId(token);
//
//            assertEquals(userId, result, "Failed for userId: " + userId);
//        }
//    }
//}