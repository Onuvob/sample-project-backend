package com.sampleproject.security;

import com.sampleproject.util.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
//    private final HandlerExceptionResolver resolver;

//    public JwtAuthenticationFilter(
//            JwtService jwtService,
//            UserDetailsServiceImpl userDetailsService,
//            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver // Tells Spring exactly which bean to pick
//    ) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//        this.resolver = resolver;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
//        String email = jwtService.extractUsername(token);

        String email = null;

        try {
            // This throws ExpiredJwtException if the token is expired
            email = jwtService.extractUsername(token);
        } catch (ExpiredJwtException e) {
            log.warn(e.getMessage());
            // Token is expired. We catch it and do nothing.
            // The user remains unauthenticated.
        } catch (JwtException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            // Token is invalid, malformed, or empty.
            // The user remains unauthenticated.
        }

//        try {
//            email = jwtService.extractUsername(token);
//        } catch (ExpiredJwtException e) {
//            // 3. SEND THE EXCEPTION TO GLOBAL EXCEPTION HANDLER
//            resolver.resolveException(request, response, null, e);
//            return; // CRITICAL: Stop the filter chain here!
//        } catch (JwtException | IllegalArgumentException e) {
//            // Handle invalid/malformed tokens as a generic RuntimeException
//            resolver.resolveException(request, response, null, new RuntimeException("Invalid or malformed token"));
//            return; // Stop the filter chain
//        }


        if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){

            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if(jwtService.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (UsernameNotFoundException e) {

                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
