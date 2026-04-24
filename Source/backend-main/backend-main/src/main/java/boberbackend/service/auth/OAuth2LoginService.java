/*
 * Bober Clinic note: Defines the methods that this service must provide.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/auth/OAuth2LoginService.java
 */
package boberbackend.service.auth;

import boberbackend.controllers.common.BadRequestException;
import org.springframework.security.core.Authentication;

public interface OAuth2LoginService {

    public Authentication processGithubOauth2Login(String code) throws BadRequestException;

}



