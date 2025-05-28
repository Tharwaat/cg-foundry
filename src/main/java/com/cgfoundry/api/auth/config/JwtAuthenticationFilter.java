package com.cgfoundry.api.auth.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final List<String> allowedPaths = List.of("/", "/auth/**");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isRequestPathAllowed(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestAuthHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (requestAuthHeader != null ) {
            String[] bearerToken = requestAuthHeader.split(" ");
            if(bearerToken.length == 2 && bearerToken[0].equals("Bearer")) {
                validateAuthToken(bearerToken[1]);
            }
        } else {
            log.error("Missing Authorization Header");
        }
        filterChain.doFilter(request, response);
    }

    private boolean isRequestPathAllowed(HttpServletRequest request) {
        String path = request.getServletPath();
        return allowedPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private void validateAuthToken(String token) {
        try {
            Optional<Authentication> authenticatedUser = this.jwtService.validateToken(token);
            if (authenticatedUser.isPresent()) {
                log.info("Valid token: {}", authenticatedUser.get());

                SecurityContextHolder.getContext().setAuthentication(authenticatedUser.get());
            } else {
                log.error("Invalid token");
                SecurityContextHolder.clearContext();
            }
        } catch (IllegalArgumentException exception) {
            log.error("Illegal Argument: ", exception);
        } catch (MalformedJwtException exception) {
            log.error("Malformed Token: ", exception);
        } catch (ExpiredJwtException exception) {
            log.error("Expired Token: ", exception);
        }
    }
}