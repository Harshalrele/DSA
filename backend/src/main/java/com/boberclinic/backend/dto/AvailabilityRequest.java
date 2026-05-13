package com.boberclinic.backend.dto;

// Data sent when a doctor adds one available appointment time.
public record AvailabilityRequest(
        // Doctor email is checked again on the backend.
        String doctorEmail,

        // Appointment start time.
        String dateTime,

        // Appointment end time.
        String endDateTime
) {
}
