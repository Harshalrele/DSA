package com.boberclinic.backend.service;

// Request and response objects used by the API.
import com.boberclinic.backend.dto.*;

// Database models used by the clinic app.
import com.boberclinic.backend.model.*;

// Repositories handle database access.
import com.boberclinic.backend.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// Service layer with the clinic rules and role checks.
@Service
public class ClinicService {
    // Matches the date text sent by the frontend date and time inputs.
    private static final DateTimeFormatter VISIT_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // User table access.
    private final UserRepository userRepository;

    // Appointment table access.
    private final AppointmentRepository appointmentRepository;

    // Doctor availability table access.
    private final AvailabilitySlotRepository availabilitySlotRepository;

    // Login tokens kept while the backend is running.
    private final Map<String, String> loginTokens = new ConcurrentHashMap<>();

    // Spring gives the service its repositories here.
    public ClinicService(
            UserRepository userRepository,
            AppointmentRepository appointmentRepository,
            AvailabilitySlotRepository availabilitySlotRepository
    ) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.availabilitySlotRepository = availabilitySlotRepository;
    }

    // Creates a patient, doctor, or receptionist/admin account.
    public UserAccount register(RegisterRequest request) {
        // Store email lowercase to make login easier.
        String email = clean(request.email());

        // Do not allow two accounts with the same email.
        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        // Build the database row for the new user.
        UserAccount user = new UserAccount();
        user.setEmail(email);
        user.setPassword(request.password());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setNationalId(request.nationalId());
        user.setSex(request.sex());
        user.setRole(request.role());
        user.setInsuranceId(request.insuranceId());
        user.setNpwzId(request.npwzId());
        user.setSpecialization(request.specialization());
        return userRepository.save(user);
    }

    // Checks login details and creates a token for protected requests.
    public LoginResponse login(LoginRequest request) {
        UserAccount user = findUser(clean(request.email()));
        if (!user.getPassword().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }
        String token = UUID.randomUUID().toString();
        loginTokens.put(token, user.getEmail());
        return new LoginResponse(user, token);
    }

    // Any logged-in user can load doctors.
    public List<UserAccount> doctors(String authorization) {
        requireLogin(authorization);
        return userRepository.findByRole(Role.DOCTOR);
    }

    // Only doctors and receptionist/admin users can load patients.
    public List<UserAccount> patients(String authorization) {
        requireAnyRole(authorization, Role.DOCTOR, Role.RECEPTIONIST_ADMIN);
        return userRepository.findByRole(Role.PATIENT);
    }

    // Only receptionist/admin users can load admin accounts.
    public List<UserAccount> admins(String authorization) {
        requireRole(authorization, Role.RECEPTIONIST_ADMIN);
        return userRepository.findByRole(Role.RECEPTIONIST_ADMIN);
    }

    // Doctor-only action: add one available appointment time.
    public AvailabilitySlot addAvailability(String authorization, AvailabilityRequest request) {
        UserAccount doctor = requireRole(authorization, Role.DOCTOR);
        String startTime = cleanTime(request.dateTime());
        String endTime = cleanTime(request.endDateTime());

        // A visit must have both a start and an end time.
        if (startTime.isBlank() || endTime.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end time are required");
        }

        // The end time must be after the start time.
        if (!parseTime(endTime).isAfter(parseTime(startTime))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time must be after start time");
        }

        // Do not create the same doctor time twice.
        if (availabilitySlotRepository.findByDoctorEmailAndDateTime(doctor.getEmail(), startTime).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This time slot is already occupied.");
        }

        AvailabilitySlot slot = new AvailabilitySlot();
        slot.setDoctorEmail(doctor.getEmail());
        slot.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
        slot.setDoctorSpecialization(doctor.getSpecialization());
        slot.setDateTime(startTime);
        slot.setEndDateTime(endTime);
        slot.setBooked(false);
        return availabilitySlotRepository.save(slot);
    }

    // Loads availability slots for booking and doctor schedules.
    public List<AvailabilitySlot> availability(String authorization, String doctorEmail, boolean onlyOpen) {
        requireLogin(authorization);
        List<AvailabilitySlot> slots;
        if (onlyOpen) {
            slots = availabilitySlotRepository.findByBookedFalse();
        } else if (doctorEmail != null && !doctorEmail.isBlank()) {
            slots = availabilitySlotRepository.findByDoctorEmail(clean(doctorEmail));
        } else {
            slots = availabilitySlotRepository.findAll();
        }

        // Old times are hidden so patients cannot book expired visits.
        return slots.stream()
                .filter(slot -> !isPast(slot.getDateTime()))
                .toList();
    }

    // Patient-only action: book one available doctor slot.
    // Synchronized stops two patients booking the same slot at the same moment.
    public synchronized Appointment createAppointment(String authorization, AppointmentRequest request) {
        UserAccount loggedInPatient = requireRole(authorization, Role.PATIENT);
        String doctorEmail = clean(request.doctorEmail());
        String patientEmail = loggedInPatient.getEmail();

        // Stop double-booking for the same doctor and time.
        if (appointmentRepository.existsByDoctorEmailAndDateTimeAndStatusNot(doctorEmail, request.dateTime(), "History")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This time slot is already occupied.");
        }

        // Check that the selected accounts have the correct roles.
        UserAccount patient = findUser(patientEmail);
        UserAccount doctor = findUser(doctorEmail);
        if (patient.getRole() != Role.PATIENT || doctor.getRole() != Role.DOCTOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment needs one patient and one doctor");
        }

        // The requested time must come from the doctor's availability table.
        AvailabilitySlot slot = availabilitySlotRepository.findByDoctorEmailAndDateTime(doctorEmail, request.dateTime())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Choose an available time slot"));
        if (isPast(slot.getDateTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Choose an available time slot");
        }
        if (slot.isBooked()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This time slot is already occupied.");
        }

        // Build the database row for the appointment.
        Appointment appointment = new Appointment();
        appointment.setPatientEmail(patient.getEmail());
        appointment.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        appointment.setPatientInsuranceId(patient.getInsuranceId());
        appointment.setDoctorEmail(doctor.getEmail());
        appointment.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
        appointment.setDoctorSpecialization(doctor.getSpecialization());
        appointment.setDateTime(slot.getDateTime());
        appointment.setEndDateTime(slot.getEndDateTime());
        appointment.setVisitReason(request.visitReason());
        appointment.setStatus("Scheduled");
        appointment.setLabTests("");
        appointment.setPhysicalTests("");
        appointment.setDiagnosis("");
        appointment.setResultOfInterview("");
        appointment.setNotes("");

        // Mark the slot as booked after the appointment is saved.
        slot.setBooked(true);
        availabilitySlotRepository.save(slot);
        return appointmentRepository.save(appointment);
    }

    // Doctor-only action: save consultation work.
    public Appointment updateAppointment(String authorization, Long id, DoctorUpdateRequest request) {
        UserAccount doctor = requireRole(authorization, Role.DOCTOR);
        Appointment appointment = findAppointment(id);
        if (!appointment.getDoctorEmail().equals(doctor.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Doctors can update only their own appointments");
        }
        appointment.setStatus(request.status());
        appointment.setLabTests(request.labTests());
        appointment.setPhysicalTests(request.physicalTests());
        appointment.setDiagnosis(request.diagnosis());
        appointment.setResultOfInterview(request.resultOfInterview());
        appointment.setNotes(request.notes());
        return appointmentRepository.save(appointment);
    }

    // Admin-only action: edit the saved time of a history appointment.
    public Appointment updateHistoryTime(String authorization, Long id, AppointmentTimeRequest request) {
        requireRole(authorization, Role.RECEPTIONIST_ADMIN);
        Appointment appointment = findAppointment(id);
        String startTime = cleanTime(request.dateTime());
        String endTime = cleanTime(request.endDateTime());

        // Only history records can be changed from the history screen.
        if (!"History".equals(appointment.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only history appointments can be edited here");
        }

        // A history time must still have both a start and an end.
        if (startTime.isBlank() || endTime.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end time are required");
        }

        // The consultation cannot end before it starts.
        if (!parseTime(endTime).isAfter(parseTime(startTime))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time must be after start time");
        }

        appointment.setDateTime(startTime);
        appointment.setEndDateTime(endTime);
        return appointmentRepository.save(appointment);
    }

    // Admin-only action: move an appointment to history and reopen its time slot.
    public void deleteAppointment(String authorization, Long id) {
        requireRole(authorization, Role.RECEPTIONIST_ADMIN);
        Appointment appointment = findAppointment(id);
        availabilitySlotRepository.findByDoctorEmailAndDateTime(appointment.getDoctorEmail(), appointment.getDateTime())
                .ifPresent(slot -> {
                    slot.setBooked(false);
                    availabilitySlotRepository.save(slot);
                });
        appointment.setStatus("History");
        appointmentRepository.save(appointment);
    }

    // Appointment list depends on the role of the logged-in user.
    public List<Appointment> appointments(String authorization, String patientEmail, String doctorEmail) {
        UserAccount requester = requireLogin(authorization);

        // Patients only see their own appointments.
        if (requester.getRole() == Role.PATIENT) {
            return appointmentRepository.findByPatientEmail(requester.getEmail());
        }

        // Doctors only see appointments assigned to them.
        if (requester.getRole() == Role.DOCTOR) {
            return appointmentRepository.findByDoctorEmail(requester.getEmail());
        }

        // Receptionist/Admin users can filter or view all appointments.
        if (doctorEmail != null && !doctorEmail.isBlank()) {
            return appointmentRepository.findByDoctorEmail(clean(doctorEmail));
        }
        if (patientEmail != null && !patientEmail.isBlank()) {
            return appointmentRepository.findByPatientEmail(clean(patientEmail));
        }
        return appointmentRepository.findAll();
    }

    // Checks that the request has a valid login token.
    private UserAccount requireLogin(String authorization) {
        String token = readToken(authorization);
        String email = loginTokens.get(token);
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required");
        }
        return findUser(email);
    }

    // Checks that the logged-in user has one exact role.
    private UserAccount requireRole(String authorization, Role role) {
        UserAccount user = requireLogin(authorization);
        if (user.getRole() != role) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong role for this action");
        }
        return user;
    }

    // Checks that the logged-in user has one of the allowed roles.
    private UserAccount requireAnyRole(String authorization, Role... roles) {
        UserAccount user = requireLogin(authorization);
        for (Role role : roles) {
            if (user.getRole() == role) {
                return user;
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong role for this action");
    }

    // Keeps the token value and removes the Bearer text.
    private String readToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required");
        }
        return authorization.substring("Bearer ".length()).trim();
    }

    // Finds one appointment or returns an error.
    private Appointment findAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment not found"));
    }

    // Finds one user by email or returns an error.
    private UserAccount findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    // Trims spaces and makes emails lowercase.
    private String clean(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    // Trims spaces without changing date and time text.
    private String cleanTime(String value) {
        return value == null ? "" : value.trim();
    }

    // Converts frontend date text into a Java date object.
    private LocalDateTime parseTime(String value) {
        try {
            return LocalDateTime.parse(value, VISIT_TIME_FORMAT);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Use a valid date and time");
        }
    }

    // Checks if a doctor time slot has already passed.
    private boolean isPast(String value) {
        try {
            return LocalDateTime.parse(value, VISIT_TIME_FORMAT).isBefore(LocalDateTime.now());
        } catch (RuntimeException exception) {
            return true;
        }
    }
}
