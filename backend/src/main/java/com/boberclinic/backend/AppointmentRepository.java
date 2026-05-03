package com.boberclinic.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Interface for database operations related to Appointments
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Get all appointments for a specific patient using their email
    List<Appointment> findByPatientEmail(String patientEmail);

    // Get all appointments for a specific doctor using their email
    List<Appointment> findByDoctorEmail(String doctorEmail);
}
