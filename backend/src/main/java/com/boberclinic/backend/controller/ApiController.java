package com.boberclinic.backend.controller;

// Request and response classes used by the API.
import com.boberclinic.backend.dto.*;

// Database objects returned by some endpoints.
import com.boberclinic.backend.model.*;

// ClinicService does the real work and role checks.
import com.boberclinic.backend.service.ClinicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Main API controller for the clinic backend.
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {
    // Business logic layer used by every endpoint.
    private final ClinicService clinicService;

    // Spring creates the controller with the service.
    public ApiController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    // Public endpoint for account creation.
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAccount register(@RequestBody RegisterRequest request) {
        return clinicService.register(request);
    }

    // Public endpoint for sign in.
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return clinicService.login(request);
    }

    // Logged-in users can load doctor accounts.
    @GetMapping("/doctors")
    public List<UserAccount> doctors(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return clinicService.doctors(authorization);
    }

    // Staff endpoint for patient data.
    @GetMapping("/patients")
    public List<UserAccount> patients(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return clinicService.patients(authorization);
    }

    // Admin endpoint for receptionist/admin accounts.
    @GetMapping("/admins")
    public List<UserAccount> admins(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return clinicService.admins(authorization);
    }

    // Doctor endpoint for adding available times.
    @PostMapping("/availability")
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilitySlot addAvailability(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody AvailabilityRequest request
    ) {
        return clinicService.addAvailability(authorization, request);
    }

    // Logged-in users can load availability slots.
    @GetMapping("/availability")
    public List<AvailabilitySlot> availability(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(required = false) String doctorEmail,
            @RequestParam(defaultValue = "false") boolean onlyOpen
    ) {
        return clinicService.availability(authorization, doctorEmail, onlyOpen);
    }

    // Patient endpoint for booking appointments.
    @PostMapping("/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointment(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody AppointmentRequest request
    ) {
        return clinicService.createAppointment(authorization, request);
    }

    // Doctor endpoint for saving consultation details.
    @PostMapping("/appointments/{id}/doctor-update")
    public Appointment saveDoctorUpdate(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @RequestBody DoctorUpdateRequest request
    ) {
        return clinicService.updateAppointment(authorization, id, request);
    }

    // Admin endpoint for editing the time of a history appointment.
    @PostMapping("/appointments/{id}/admin-time")
    public Appointment saveHistoryTime(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @RequestBody AppointmentTimeRequest request
    ) {
        return clinicService.updateHistoryTime(authorization, id, request);
    }

    // Admin endpoint for deleting appointments.
    @DeleteMapping("/appointments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id
    ) {
        clinicService.deleteAppointment(authorization, id);
    }

    // Appointment list depends on the logged-in user's role.
    @GetMapping("/appointments")
    public List<Appointment> appointments(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(required = false) String patientEmail,
            @RequestParam(required = false) String doctorEmail
    ) {
        return clinicService.appointments(authorization, patientEmail, doctorEmail);
    }
}
