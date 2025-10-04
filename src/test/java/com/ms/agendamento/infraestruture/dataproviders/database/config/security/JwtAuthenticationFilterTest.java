package com.ms.agendamento.infraestruture.dataproviders.database.config.security;


import com.ms.agendamento.infraestruture.dataproviders.config.security.JwtAuthenticationFilter;
import com.ms.agendamento.infraestruture.dataproviders.config.security.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private PrintWriter printWriter;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_WithInvalidToken_ShouldThrowJwtException() throws ServletException, IOException {
        String token = "invalid-token";
        String authHeader = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtUtil.isTokenValid(token)).thenReturn(false);
        when(response.getWriter()).thenReturn(printWriter);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(printWriter).write("{\"error\": \"Token JWT inválido ou expirado\"}");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithNoAuthorizationHeader_ShouldContinueFilter() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_WithInvalidAuthorizationHeader_ShouldContinueFilter() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Invalid header");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_WithEmptyBearerToken_ShouldHandleException() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer ");
        when(response.getWriter()).thenReturn(printWriter);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(printWriter).write("{\"error\": \"Token JWT inválido ou expirado\"}");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WhenJwtUtilThrowsException_ShouldHandleException() throws ServletException, IOException {
        String token = "token-that-causes-exception";
        String authHeader = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtUtil.isTokenValid(token)).thenThrow(new JwtException("Token parsing error"));
        when(response.getWriter()).thenReturn(printWriter);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(printWriter).write("{\"error\": \"Token JWT inválido ou expirado\"}");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testConstructor() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        assertNotNull(filter);
    }
}