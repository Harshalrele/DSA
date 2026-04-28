package com.boberclinic.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// This class becomes the user_account table in the H2 database.
@Entity
public class UserAccount {

    // This is the automatic database ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Email is unique because it is used for login.
    @Column(unique = true, nullable = false)
    private String email;

    // Password is plain text to keep the demo easy to explain.
    @Column(nullable = false)
    private String password;

    // First name shown in the frontend.
    private String firstName;

    // Last name shown in the frontend.
    private String lastName;

    // National ID is kept as a simple text field.
    private String nationalId;

    // Sex is kept as a simple text field.
    private String sex;

    // Role decides if the account is a patient or doctor.
    @Enumerated(EnumType.STRING)
    private Role role;

    // Patients use insurance ID.
    private String insuranceId;

    // Doctors use NPWZ ID.
    private String npwzId;

    // Doctors use this for their area of expertise.
    private String specialization;

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
