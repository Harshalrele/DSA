package com.boberclinic.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This repository gives simple database commands for appointments.
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Finds all appointments created by one patient email.
    List<Appointment> findByPatientEmail(String patientEmail);

    // Finds all appointments booked with one doctor email.
    List<Appointment> findByDoctorEmail(String doctorEmail);
}
