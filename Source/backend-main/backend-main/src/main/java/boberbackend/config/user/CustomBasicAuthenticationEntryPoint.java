/*
 * Bober Clinic note: Contains code or settings for CustomBasicAuthenticationEntryPoint.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/config/user/CustomBasicAuthenticationEntryPoint.java
 */
package boberbackend.config.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}



