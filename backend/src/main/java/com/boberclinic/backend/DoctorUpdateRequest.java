package com.boberclinic.backend;

// This record describes the appointment changes saved by a doctor.
public record DoctorUpdateRequest(
        String testNotes,
        String labTests,
        String diagnosis,
        String status
) {
}
