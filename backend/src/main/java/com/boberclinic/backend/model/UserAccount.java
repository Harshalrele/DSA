package com.boberclinic.backend.model;

// Keeps passwords out of JSON responses.
import com.fasterxml.jackson.annotation.JsonProperty;

// JPA annotations connect the class to a database table.
import jakarta.persistence.*;

// One saved user account.
@Entity
public class UserAccount {
    // Database id.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique login email.
    @Column(unique = true, nullable = false)
    private String email;

    // Saved password for local login.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    // Basic personal information.
    private String firstName;
    private String lastName;
    private String nationalId;
    private String sex;

    // Patient, doctor, or receptionist/admin.
    @Enumerated(EnumType.STRING)
    private Role role;

    // Role-specific fields.
    private String insuranceId;
    private String npwzId;
    private String specialization;

    // Getters and setters used by Spring and JSON.
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getInsuranceId() { return insuranceId; }
    public void setInsuranceId(String insuranceId) { this.insuranceId = insuranceId; }
    public String getNpwzId() { return npwzId; }
    public void setNpwzId(String npwzId) { this.npwzId = npwzId; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
}
