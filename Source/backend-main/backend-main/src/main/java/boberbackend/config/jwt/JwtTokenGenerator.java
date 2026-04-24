/*
 * Bober Clinic note: Handles JWT token creation, validation, or filtering.
 * File: backend-main/backend-main/src/main/java/boberbackend/config/jwt/JwtTokenGenerator.java
 */
package boberbackend.config.jwt;

import boberbackend.jpa.model.AppUser;
import boberbackend.jpa.model.Person;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtEncoder jwtEncoder;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenGenerator.class);

    public String generateAccessToken(Authentication authentication, AppUser appUser) {
        logger.info("[generateAccessToken] Token creation started for: {}", authentication.getName());
        String role = getUserRole(authentication);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Bober")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", role)
                .claim("firstName", getUserFirstName(appUser))
                .claim("lastName", getUserLastName(appUser))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(Authentication authentication) {

        logger.info("[generateRefreshToken] Token Creation Started for: {}", authentication.getName());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Bober")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(15, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scope", "REFRESH_TOKEN")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String getUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(""));
    }

    private String getUserFirstName(AppUser appUser) {
        return appUser.getPerson().getFirstName();
    }

    private String getUserLastName(AppUser appUser) {
        return appUser.getPerson().getLastName();
    }

}




