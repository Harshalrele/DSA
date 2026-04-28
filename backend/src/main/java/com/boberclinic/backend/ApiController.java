package com.boberclinic.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// This controller contains all backend API routes used by the frontend.
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    // This repository saves and reads users.
    private final UserRepository userRepository;

    // This repository saves and reads appointments.
    private final AppointmentRepository appointmentRepository;

    // Spring gives the controller both repositories here.
    public ApiController(UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Creates a patient or doctor account.
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAccount register(@RequestBody RegisterRequest request) {
        // Email is stored lowercase so login is easier.
        String email = clean(request.email());

        // The same email cannot be used twice.
        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        // Create a new empty user object.
        UserAccount user = new UserAccount();

        // Save the common account fields.
        user.setEmail(email);
        user.setPassword(request.password());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setNationalId(request.nationalId());
        user.setSex(request.sex());
        user.setRole(request.role());

        // Save patient-only or doctor-only fields.
        if (request.role() == Role.PATIENT) {
            user.setInsuranceId(request.insuranceId());
        } else {
            user.setNpwzId(request.npwzId());
            user.setSpecialization(request.specialization());
        }

        // Store the account in the H2 database.
        return userRepository.save(user);
    }

    // Logs in by checking email and password in the database.
    @PostMapping("/login")
    public UserAccount login(@RequestBody LoginRequest request) {
        // Email is cleaned the same way as registration.
        String email = clean(request.email());

        // Find the user or return an error.
        UserAccount user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials"));

        // Check the plain text password for this simple demo.
        if (!user.getPassword().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }

        // Return the logged-in user to the frontend.
        return user;
    }

    // Returns all doctor accounts for the appointment dropdown.
    @GetMapping("/doctors")
    public List<UserAccount> doctors() {
        return userRepository.findByRole(Role.DOCTOR);
    }

    // Returns all patient accounts for the doctor dashboard.
    @GetMapping("/patients")
    public List<UserAccount> patients() {
        return userRepository.findByRole(Role.PATIENT);
    }

    // Creates an appointment for a patient with a selected doctor.
    @PostMapping("/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointment(@RequestBody AppointmentRequest request) {
        // Find the patient who is creating the appointment.
        UserAccount patient = userRepository.findByEmail(clean(request.patientEmail()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient not found"));

        // Only patient accounts can create appointments.
        if (patient.getRole() != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only patients can create appointments");
        }

        // Find the selected doctor by email.
        UserAccount doctor = userRepository.findByEmail(clean(request.doctorEmail()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor not found"));

        // Only doctor accounts can be used as doctors.
        if (doctor.getRole() != Role.DOCTOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selected user is not a doctor");
        }

        // Create the appointment object.
        Appointment appointment = new Appointment();

        // Save patient, doctor, and date information.
        appointment.setPatientEmail(patient.getEmail());
        appointment.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        appointment.setPatientInsuranceId(patient.getInsuranceId());
        appointment.setDoctorEmail(doctor.getEmail());
        appointment.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
        appointment.setDoctorSpecialization(doctor.getSpecialization());
        appointment.setDateTime(request.dateTime());
        appointment.setVisitReason(request.visitReason());
        appointment.setTestNotes("");
        appointment.setLabTests("");
        appointment.setDiagnosis("");
        appointment.setStatus("Scheduled");

        // Store the appointment in the H2 database.
        return appointmentRepository.save(appointment);
    }

    // Saves appointment changes written by the doctor.
    @PostMapping("/appointments/{id}/doctor-update")
    public Appointment saveDoctorUpdate(@PathVariable Long id, @RequestBody DoctorUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment not found"));
        appointment.setTestNotes(request.testNotes());
        appointment.setLabTests(request.labTests());
        appointment.setDiagnosis(request.diagnosis());
        appointment.setStatus(request.status());
        return appointmentRepository.save(appointment);
    }

    // Returns appointments for one patient or one doctor.
    @GetMapping("/appointments")
    public List<Appointment> appointments(
            @RequestParam(required = false) String patientEmail,
            @RequestParam(required = false) String doctorEmail
    ) {
        if (doctorEmail != null && !doctorEmail.isBlank()) {
            return appointmentRepository.findByDoctorEmail(clean(doctorEmail));
        }
        return appointmentRepository.findByPatientEmail(clean(patientEmail));
    }

    // Small helper that removes spaces and lowercases emails.
    private String clean(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
