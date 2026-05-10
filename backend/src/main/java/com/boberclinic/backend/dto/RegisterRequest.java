package com.boberclinic.backend.dto;

// Account role selected on the register form.
import com.boberclinic.backend.model.Role;

// Data sent from the create-account form.
public record RegisterRequest(
        // Login email.
        String email,

        // Login password.
        String password,

        // User name.
        String firstName,
        String lastName,

        // Personal information.
        String nationalId,
        String sex,

        // Account type.
        Role role,

        // Patient identifier.
        String insuranceId,

        // Doctor information.
        String npwzId,
        String specialization
) {
}
