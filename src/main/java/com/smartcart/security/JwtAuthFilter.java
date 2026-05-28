package com.smartcart.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Allow Swagger URLs without JWT authentication
        String path = request.getServletPath();

        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        System.out.println("JWT Filter Executed");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            System.out.println("Token Received: " + token);

            if (JwtUtil.validateToken(token)
                    && SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

                System.out.println("Token Valid");

                String email = JwtUtil.extractEmail(token);
                String role = JwtUtil.extractRole(token);

                System.out.println("Email Extracted: " + email);
                System.out.println("Role Extracted: " + role);

                List<SimpleGrantedAuthority> authorities =
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + role
                                )
                        );

                System.out.println("Authorities: " + authorities);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                authorities
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                System.out.println("Authentication Set Successfully");

            } else {
                System.out.println("Token Invalid");
            }

        } else {
            System.out.println("Authorization Header Missing");
        }

        filterChain.doFilter(request, response);
    }
}