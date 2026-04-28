package com.boberclinic.backend;

// This record describes the JSON sent by the appointment form.
public record AppointmentRequest(
        String patientEmail,
        String doctorEmail,
        String dateTime,
        String visitReason
) {
}
