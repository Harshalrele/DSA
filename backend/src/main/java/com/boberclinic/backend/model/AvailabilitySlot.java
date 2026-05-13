package com.boberclinic.backend.model;

// JPA annotations connect the class to a database table.
import jakarta.persistence.*;

// One possible appointment start time for a doctor.
@Entity
public class AvailabilitySlot {
    // Database id.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Doctor who owns the time slot.
    private String doctorEmail;
    private String doctorName;
    private String doctorSpecialization;

    // Date and start time selected by the doctor.
    private String dateTime;

    // Date and end time selected by the doctor.
    private String endDateTime;

    // True after a patient books the slot.
    private boolean booked;

    // Getters and setters used by Spring and JSON.
    public Long getId() { return id; }
    public String getDoctorEmail() { return doctorEmail; }
    public void setDoctorEmail(String doctorEmail) { this.doctorEmail = doctorEmail; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public String getDoctorSpecialization() { return doctorSpecialization; }
    public void setDoctorSpecialization(String doctorSpecialization) { this.doctorSpecialization = doctorSpecialization; }
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public String getEndDateTime() { return endDateTime; }
    public void setEndDateTime(String endDateTime) { this.endDateTime = endDateTime; }
    public boolean isBooked() { return booked; }
    public void setBooked(boolean booked) { this.booked = booked; }
}
