/*
 * Bober Clinic note: Handles JWT token creation, validation, or filtering.
 * File: backend-main/backend-main/src/main/java/boberbackend/config/jwt/ValidationExceptionHandler.java
 */
package boberbackend.config.jwt;

import boberbackend.controllers.common.BadRequestRes;
import boberbackend.enums.BadRequestDictEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import java.io.IOException;

public class ValidationExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handleJwtExpiredException(HttpServletResponse response, JwtValidationException e) throws IOException {
        logger.error("[handleJwtExpiredException] JWT Expired {}", e.getMessage());
        BadRequestRes badRequestRes = new BadRequestRes(BadRequestDictEnum.EXPIRED_JWT, e.getMessage());
        String jsonResponse = objectMapper.writeValueAsString(badRequestRes);
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    public static void handleMissingUserException(HttpServletResponse response, UsernameNotFoundException e) throws IOException {
        logger.error("[handleMissingUserException] Bad user in token {}", e.getMessage());
        BadRequestRes badRequestRes = new BadRequestRes(BadRequestDictEnum.BAD_USER, e.getMessage());
        String jsonResponse = objectMapper.writeValueAsString(badRequestRes);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}



