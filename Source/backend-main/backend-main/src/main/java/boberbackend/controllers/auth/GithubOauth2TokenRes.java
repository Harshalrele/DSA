/*
 * Bober Clinic note: Describes response data sent from the backend to the frontend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/auth/GithubOauth2TokenRes.java
 */
package boberbackend.controllers.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubOauth2TokenRes {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("token_type")
    private String tokenType;
}



