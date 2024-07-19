package com.mt.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider userAuthenticationProvider;

    public static final String BEARER = "Bearer";
    public static final String GET = "GET";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(header)) {
            var authElements = header.split(" ");
            if (isValidFormatToken(authElements)) {
                try {
                    SecurityContextHolder.getContext().setAuthentication(getAuthentication(request, authElements[1]));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletRequest request, String firstAuthElement) {
        return isValidTypeMethod(request)
                ? userAuthenticationProvider.validateToken(firstAuthElement)
                : userAuthenticationProvider.validateTokenStrongly(firstAuthElement);
    }

    private static boolean isValidTypeMethod(HttpServletRequest request) {
        return Objects.equals(request.getMethod(), GET);
    }

    private Boolean isValidFormatToken(String[] authElements) {
        return Objects.equals(authElements.length, 2) && BEARER.equals(authElements[0]);
    }
}
