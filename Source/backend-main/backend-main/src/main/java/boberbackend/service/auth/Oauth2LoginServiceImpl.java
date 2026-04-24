package boberbackend.service.auth;

import boberbackend.controllers.auth.GithubOauth2TokenReq;
import boberbackend.controllers.auth.GithubOauth2TokenRes;
import boberbackend.controllers.auth.GithubUserRes;
import boberbackend.controllers.common.BadRequestException;
import boberbackend.enums.BadRequestDictEnum;
import boberbackend.jpa.model.AppUser;
import boberbackend.jpa.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Oauth2LoginServiceImpl implements OAuth2LoginService {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2LoginService.class);
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubClientSecret;

    private final String githubUserApiUrl = "https://api.github.com/user";
    private final String githubTokenApiUrl = "https://github.com/login/oauth/access_token";

    private final AppUserRepository appUserRepository;

    @Override
    public Authentication processGithubOauth2Login(String code) throws BadRequestException {

        String token;
        GithubUserRes userResponse;

        logger.info("[processGithubOauth2Login] ");

        try {
            token = getGithubToken(code);
            userResponse = getGithubUser(token);
        } catch (HttpClientErrorException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_OAUTH2_LOGIN, e.getMessage());
        } catch (NullPointerException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_OAUTH2_LOGIN, null);
        }

        AppUser user = appUserRepository.findByEmail(userResponse.getEmail()).orElse(null);

        return createAuthenticationObject(user);

    }

    private String getGithubToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        GithubOauth2TokenReq req = new GithubOauth2TokenReq(githubClientId, githubClientSecret, code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GithubOauth2TokenReq> request = new HttpEntity<>(req, headers);
        ResponseEntity<GithubOauth2TokenRes> response = restTemplate.postForEntity(githubTokenApiUrl, request, GithubOauth2TokenRes.class);
        return response.getBody().getAccessToken();
    }

    private GithubUserRes getGithubUser(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/vnd.github+json");
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(githubUserApiUrl));

        ResponseEntity<GithubUserRes> response = restTemplate.exchange(requestEntity, GithubUserRes.class);
        return response.getBody();
    }

    private static Authentication createAuthenticationObject(AppUser appUser) {
        String username = appUser.getEmail();
        String role = appUser.getRole().name();
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        return new UsernamePasswordAuthenticationToken(username, null, List.of(authority));
    }

}

