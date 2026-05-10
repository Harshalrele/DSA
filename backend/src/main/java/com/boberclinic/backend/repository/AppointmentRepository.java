package com.boberclinic.backend.repository;

// Appointment database model.
import com.boberclinic.backend.model.Appointment;

// JpaRepository provides common database methods.
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Appointment database queries.
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Appointments for one patient.
    List<Appointment> findByPatientEmail(String patientEmail);

    // Appointments for one doctor.
    List<Appointment> findByDoctorEmail(String doctorEmail);

    // Prevents one doctor from being booked twice at the same time.
    boolean existsByDoctorEmailAndDateTime(String doctorEmail, String dateTime);
}
