package com.boberclinic.backend.dto;

// Data sent when a doctor saves consultation work.
public record DoctorUpdateRequest(
        // Scheduled, Consultation, or Completed.
        String status,

        // Selected lab tests.
        String labTests,

        // Selected physical tests.
        String physicalTests,

        // Doctor diagnosis.
        String diagnosis,

        // Interview result or patient description.
        String resultOfInterview,

        // Extra remarks.
        String notes
) {
}
