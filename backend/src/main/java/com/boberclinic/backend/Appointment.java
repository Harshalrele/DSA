package com.boberclinic.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class representing a medical appointment within the clinic system.
 * This class is mapped to the persistence layer in the H2 database.
 */
@Entity
public class Appointment {

    // Unique identifier for the appointment, automatically generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Email reference for the patient account
    private String patientEmail;

    // Email reference for the assigned doctor
    private String doctorEmail;

    // Full name of the patient for quick UI access
    private String patientName;

    // Patient's medical insurance identifier
    private String patientInsuranceId;

    // Full name of the assigned medical professional
    private String doctorName;

    // Medical area or expertise of the doctor
    private String doctorSpecialization;

    // Scheduled date and time (stored as String for simplicity)
    private String dateTime;

    // Patient's description regarding the visit motive
    private String visitReason;

    // General clinical notes related to the visit
    private String testNotes;

    // Requested laboratory tests or examinations
    private String labTests;

    // Final clinical diagnosis provided by the doctor
    private String diagnosis;

    // Current workflow status (e.g., "Scheduled", "Completed")
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
