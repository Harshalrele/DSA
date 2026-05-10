package com.boberclinic.backend.dto;

// Data sent when a doctor adds one available start time.
public record AvailabilityRequest(String doctorEmail, String dateTime) {
}
