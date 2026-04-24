package boberbackend.config.jwt;

import boberbackend.config.user.CustomUserDetails;
import boberbackend.jpa.model.AppUser;
import boberbackend.jpa.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    private final AppUserRepository appUserRepository;

    public String getUsername(Jwt token) {
        return token.getSubject();
    }

    public boolean isTokenValid(Jwt token, CustomUserDetails userDetails) {
        final String userName = getUsername(token);
        boolean isTokenExpired = getIfTokenIsExpired(token);
        boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getUsername());
        return !isTokenExpired && isTokenUserSameAsDatabase;

    }

    private boolean getIfTokenIsExpired(Jwt token) {
        return Objects.requireNonNull(token.getExpiresAt()).isBefore(Instant.now());
    }

    public CustomUserDetails getUserDetails(String email) {
        return appUserRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + email + " does not exist"));
    }


}

