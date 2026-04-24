/*
 * Bober Clinic note: Describes response data sent from the backend to the frontend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/auth/GithubUserRes.java
 */
package boberbackend.controllers.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubUserRes {
    @JsonProperty("login")
    private String login;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("email")
    private String email;
}



