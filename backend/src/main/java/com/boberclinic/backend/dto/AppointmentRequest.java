package com.boberclinic.backend.dto;

// Data sent when a patient books an appointment.
public record AppointmentRequest(
        // Patient email.
        String patientEmail,

        // Selected doctor email.
        String doctorEmail,

        // Selected date and start time.
        String dateTime,

        // Patient symptom or illness description.
        String visitReason
) {
}
