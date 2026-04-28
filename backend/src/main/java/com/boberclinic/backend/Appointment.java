package com.boberclinic.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// This class becomes the appointment table in the H2 database.
@Entity
public class Appointment {

    // This is the automatic appointment ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The patient email connects the appointment to the logged-in patient.
    private String patientEmail;

    // The doctor email connects the appointment to the selected doctor.
    private String doctorEmail;

    // Patient name is saved so the doctor can see who booked the visit.
    private String patientName;

    // Patient insurance ID is saved as a simple patient identifier.
    private String patientInsuranceId;

    // Doctor name is saved so it is easy to show in the frontend.
    private String doctorName;

    // Doctor expertise is saved so the appointment is easy to read.
    private String doctorSpecialization;

    // Date and time are stored as simple text for this demo.
    private String dateTime;

    // Visit reason stores the patient's short visit note.
    private String visitReason;

    // Test notes store simple test information for the visit.
    private String testNotes;

    // Lab tests selected by the doctor, saved as simple text.
    private String labTests;

    // Diagnosis written by the doctor.
    private String diagnosis;

    // Appointment status, for example Scheduled or Completed.
    private String status;

    public Long getId() {
        return id;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientInsuranceId() {
        return patientInsuranceId;
    }

    public void setPatientInsuranceId(String patientInsuranceId) {
        this.patientInsuranceId = patientInsuranceId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getTestNotes() {
        return testNotes;
    }

    public void setTestNotes(String testNotes) {
        this.testNotes = testNotes;
    }

    public String getLabTests() {
        return labTests;
    }

    public void setLabTests(String labTests) {
        this.labTests = labTests;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
