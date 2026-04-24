package boberbackend.service.auth;

import boberbackend.controllers.auth.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    public AuthResponse getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response);

    public AuthResponse getAccessTokenUsingRefreshToken(HttpServletRequest request);

}

