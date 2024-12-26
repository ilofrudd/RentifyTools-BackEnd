package org.rentifytools.security.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

    public static Long getCurrentUserId() {
        var authentication = getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof String)) {
            throw new IllegalStateException("User is not authenticated");
        }
        return Long.valueOf(authentication.getPrincipal().toString());
    }

    public static String getCurrentUserEmail() {
        var authentication = getAuthentication();
        return authentication.getName();
    }

    private static Authentication getAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authorized");
        }
        return authentication;
    }
}
