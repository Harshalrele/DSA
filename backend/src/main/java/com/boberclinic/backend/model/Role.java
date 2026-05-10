package com.boberclinic.backend.model;

// These are the three account types used by the project.
public enum Role {
    // A patient books appointments and sees only their own appointments.
    PATIENT,

    // A doctor adds availability and completes consultations.
    DOCTOR,

    // A receptionist/admin checks clinic data and appointment history.
    RECEPTIONIST_ADMIN
}
