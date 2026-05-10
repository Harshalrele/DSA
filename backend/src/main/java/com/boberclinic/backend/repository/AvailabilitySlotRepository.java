package com.boberclinic.backend.repository;

// Availability database model.
import com.boberclinic.backend.model.AvailabilitySlot;

// JpaRepository provides common database methods.
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Availability database queries.
public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {
    // All slots for one doctor.
    List<AvailabilitySlot> findByDoctorEmail(String doctorEmail);

    // Slots still open for booking.
    List<AvailabilitySlot> findByBookedFalse();

    // Exact slot by doctor and date/time.
    Optional<AvailabilitySlot> findByDoctorEmailAndDateTime(String doctorEmail, String dateTime);
}
