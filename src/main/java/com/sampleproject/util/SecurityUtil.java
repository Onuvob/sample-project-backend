package com.sampleproject.util;

import com.sampleproject.user.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public final class SecurityUtil {



    private SecurityUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Optional<Authentication> getCurrentAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    public static boolean isAuthenticated() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static Optional<String> getCurrentUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return Optional.of(userDetails.getUsername());
        }

        return Optional.ofNullable(authentication.getName());
    }

    public static List<String> getCurrentUserRoles() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return List.of();
        }

        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public static boolean hasRole(String role) {
        return getCurrentUserRoles().contains(role);
    }
}
