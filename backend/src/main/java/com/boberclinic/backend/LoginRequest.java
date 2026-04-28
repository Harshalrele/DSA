package com.boberclinic.backend;

// This record describes the JSON sent by the login form.
public record LoginRequest(String email, String password) {
}
