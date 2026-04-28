package com.boberclinic.backend;

// This record describes the JSON sent by the create account form.
public record RegisterRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String nationalId,
        String sex,
        Role role,
        String insuranceId,
        String npwzId,
        String specialization
) {
}
