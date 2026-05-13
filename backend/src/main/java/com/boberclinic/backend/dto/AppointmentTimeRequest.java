package com.boberclinic.backend.dto;

// Data sent when reception edits the time of a history appointment.
public record AppointmentTimeRequest(
        // New appointment start time.
        String dateTime,

        // New appointment end time.
        String endDateTime
) {
}
