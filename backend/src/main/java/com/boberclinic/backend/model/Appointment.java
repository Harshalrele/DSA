package com.boberclinic.backend.model;

// JPA annotations connect the class to a database table.
import jakarta.persistence.*;

// One saved appointment.
@Entity
public class Appointment {
    // Database id.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patient details copied onto the appointment.
    private String patientEmail;
    private String patientName;
    private String patientInsuranceId;

    // Doctor details copied onto the appointment.
    private String doctorEmail;
    private String doctorName;
    private String doctorSpecialization;

    // Appointment start time, end time, and current status.
    private String dateTime;
    private String endDateTime;
    private String status;

    // Patient symptom or illness description.
    private String visitReason;

    // Consultation fields filled by the doctor.
    private String labTests;
    private String physicalTests;
    private String diagnosis;
    private String resultOfInterview;
    private String notes;

    // Getters and setters used by Spring and JSON.
    public Long getId() { return id; }
    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getPatientInsuranceId() { return patientInsuranceId; }
    public void setPatientInsuranceId(String patientInsuranceId) { this.patientInsuranceId = patientInsuranceId; }
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getVisitReason() { return visitReason; }
    public void setVisitReason(String visitReason) { this.visitReason = visitReason; }
    public String getLabTests() { return labTests; }
    public void setLabTests(String labTests) { this.labTests = labTests; }
    public String getPhysicalTests() { return physicalTests; }
    public void setPhysicalTests(String physicalTests) { this.physicalTests = physicalTests; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getResultOfInterview() { return resultOfInterview; }
    public void setResultOfInterview(String resultOfInterview) { this.resultOfInterview = resultOfInterview; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
