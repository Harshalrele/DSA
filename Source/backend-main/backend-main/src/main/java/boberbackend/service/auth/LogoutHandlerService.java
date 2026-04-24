package boberbackend.service.auth;

import boberbackend.jpa.model.RefreshToken;
import boberbackend.jpa.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class LogoutHandlerService implements LogoutHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogoutHandlerService.class);
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        logger.warn("Begining logout process for user with no authentication");

        final String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> "refresh_token".equals(cookie.getName())).findFirst().map(Cookie::getValue).orElse(null);

        if (refreshToken == null) {
            return;
        }

        RefreshToken storedRefreshToken = refreshTokenRepository.findByToken(refreshToken)
                .map(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                    return token;
                })
                .orElse(null);
    }
}

