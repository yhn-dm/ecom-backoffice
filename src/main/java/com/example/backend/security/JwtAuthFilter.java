package com.example.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {

        try {
            String path = request.getRequestURI();

            // exclusion Swagger et son OpenAPI
            if (path.startsWith("/v3/api-docs")
                    || path.startsWith("/swagger-ui")
                    || path.equals("/swagger-ui.html")) {
                filterChain.doFilter(request, response);
                return;
            }

            String header = request.getHeader("Authorization");

            if (
                    header != null &&
                            header.startsWith("Bearer ") &&
                            SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                String token = header.substring(7);

                if (jwtUtil.isTokenValid(token)) {
                    String username = jwtUtil.extractUsername(token);
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    var auth = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    auth.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }


            filterChain.doFilter(request, response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
