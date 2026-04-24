/*
 * Bober Clinic note: Contains the main business logic for this service.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/auth/AuthServiceImpl.java
 */
package boberbackend.service.auth;

import boberbackend.config.jwt.JwtTokenGenerator;
import boberbackend.controllers.auth.AuthResponse;
import boberbackend.enums.JwtTokenTypeEnum;
import boberbackend.jpa.model.AppUser;
import boberbackend.jpa.model.Person;
import boberbackend.jpa.model.RefreshToken;
import boberbackend.jpa.repository.RefreshTokenRepository;
import boberbackend.service.AppUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AppUserService appUserService;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthResponse getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response) {
        try {
            var userInfoEntity = appUserService.findByEmail(authentication.getName()).orElseThrow(() -> {
                logger.error("[getJwtTokensAfterAuthentication] User: {} not found", authentication.getName());
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
            });

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication, userInfoEntity);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);
            saveUserRefreshToken(userInfoEntity, refreshToken);
            createRefreshTokenCookie(response, refreshToken);

            logger.info("[getJwtTokensAfterAuthentication] Access token for user: {} has been generated", userInfoEntity.getEmail());
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userName(userInfoEntity.getEmail())
                    .tokenType(JwtTokenTypeEnum.BEARER)
                    .build();
        } catch (Exception e) {
            logger.error("[getJwtTokensAfterAuthentication] Exception occurred while authenticating user due to: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }

    private void saveUserRefreshToken(AppUser appUser, String refreshToken) {
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .user(appUser)
                .token(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    private Cookie createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); //in seconds
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
        return refreshTokenCookie;
    }

    public AuthResponse getAccessTokenUsingRefreshToken(HttpServletRequest request) {

        final String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> "refresh_token".equals(cookie.getName())).findFirst().map(Cookie::getValue).orElse(null);

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .filter(tokens -> !tokens.isRevoked())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh token revoked"));

        AppUser appUser = refreshTokenEntity.getUser();

        Authentication authentication = createAuthenticationObject(appUser);
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication, appUser);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .userName(appUser.getEmail())
                .tokenType(JwtTokenTypeEnum.BEARER)
                .build();
    }

    private static Authentication createAuthenticationObject(AppUser appUser) {
        String username = appUser.getEmail();
        String password = appUser.getBasicAuthUser().getPassword();
        String role = appUser.getRole().name();
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        return new UsernamePasswordAuthenticationToken(username, password, List.of(authority));
    }
}



