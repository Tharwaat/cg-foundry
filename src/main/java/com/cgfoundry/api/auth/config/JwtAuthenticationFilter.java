package com.cgfoundry.api.auth.config;

import com.cgfoundry.api.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher.withDefaults;

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
        String path = request.getServletPath();
        boolean isPublic = allowedPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            String token = requestHeader.substring(7);
            try {
                String email = this.jwtService.getEmailFromToken(token);
                Boolean validateToken = this.jwtService.validateToken(token, email);
                if (validateToken.equals(Boolean.TRUE)) {
                    log.info("Valid token");
                    filterChain.doFilter(request, response);
                } else {
                    log.error("Invalid token");
                }
            } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException e) {
                log.error("ERROR: ", e);
            }   catch (Exception e) {
                log.error("Exception");
            }
        } else {
            log.error("empty header");
        }
    }
}