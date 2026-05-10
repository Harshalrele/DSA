package com.boberclinic.backend.dto;

// Email and password from the sign-in form.
public record LoginRequest(String email, String password) {
}
