package com.saiayns.sms.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelperUtil {
	
	private SecurityHelperUtil() {}

    public static String getEmailFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal(); // Principal is the email
        }
        return null; // No authentication or principal found
    }
}
