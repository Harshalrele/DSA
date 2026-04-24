/*
 * Bober Clinic note: Defines the methods that this service must provide.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/auth/AuthService.java
 */
package boberbackend.service.auth;

import boberbackend.controllers.auth.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    public AuthResponse getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response);

    public AuthResponse getAccessTokenUsingRefreshToken(HttpServletRequest request);

}



