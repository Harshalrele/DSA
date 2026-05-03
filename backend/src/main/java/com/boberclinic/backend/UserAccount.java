package com.boberclinic.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Database entity for user accounts
@Entity
public class UserAccount {

    // Primary key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique email address used for authentication
    @Column(unique = true, nullable = false)
    private String email;

    // User password stored in plain text
    @Column(nullable = false)
    private String password;

    // User's first name
    private String firstName;

    // User's last name
    private String lastName;

    // Government or National ID number
    private String nationalId;

    // Biological sex of the user
    private String sex;

    // Defines if the user is a Doctor or a Patient
    @Enumerated(EnumType.STRING)
    private Role role;

    // Identification for medical insurance (Patients only)
    private String insuranceId;

    // Professional license number (Doctors only)
    private String npwzId;

    // Medical area of expertise (Doctors only)
    private String specialization;

    // --- GETTERS AND SETTERS ---

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getNpwzId() {
        return npwzId;
    }

    public void setNpwzId(String npwzId) {
        this.npwzId = npwzId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}