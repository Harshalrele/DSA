package com.boberclinic.backend.dto;

// Signed-in user returned with the token.
import com.boberclinic.backend.model.UserAccount;

// Response after a successful login.
public record LoginResponse(
        // User account without the password in JSON.
        UserAccount user,

        // Token used for protected requests.
        String token
) {
}
