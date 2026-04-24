/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/auth/GithubOauth2TokenReq.java
 */
package boberbackend.controllers.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GithubOauth2TokenReq {
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("code")
    private String code;
}



