package boberbackend.controllers.auth;

import boberbackend.config.db.DbRegisterService;
import boberbackend.enums.RoleEnum;
import boberbackend.jpa.repository.AppUserRepository;
import boberbackend.jpa.repository.DoctorRepository;
import boberbackend.jpa.repository.PatientRepository;
import boberbackend.service.auth.AuthService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    // Existing service that creates JWT access/refresh tokens after login.
    private final AuthService authService;
    // Helper service that creates the linked AppUser, Person, Patient, and Doctor database records.
    private final DbRegisterService dbRegisterService;
    // Repositories are used here to block duplicate registration values before saving.
    private final AppUserRepository appUserRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    // Called by the login form. Spring Security checks the Basic Auth email/password first.
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> authenticateUser(Authentication authentication, HttpServletResponse response) {
        return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication, response));
    }

    // Public endpoint used by the Register page to create patient or doctor accounts.
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody RegisterRequest request) {
        // Email must be unique because it is used as the login username.
        if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Email is already registered");
        }

        // Patient registration requires an insurance ID and creates a Patient profile.
        if (request.getRole() == RoleEnum.PATIENT) {
            if (request.getInsuranceId() == null || request.getInsuranceId().isBlank()) {
                throw new ResponseStatusException(BAD_REQUEST, "Insurance ID is required for patients");
            }
            if (patientRepository.findByInsuranceId(request.getInsuranceId()).isPresent()) {
                throw new ResponseStatusException(BAD_REQUEST, "Insurance ID is already registered");
            }
            dbRegisterService.createPatient(
                    request.getEmail(),
                    request.getNationalIdNumber(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getSex(),
                    request.getPassword(),
                    request.getInsuranceId()
            );
        // Doctor registration requires an NPWZ ID and creates a Doctor profile.
        } else if (request.getRole() == RoleEnum.DOCTOR) {
            if (request.getNpwzId() == null || request.getNpwzId().isBlank()) {
                throw new ResponseStatusException(BAD_REQUEST, "NPWZ ID is required for doctors");
            }
            if (doctorRepository.existsByNpwzId(request.getNpwzId())) {
                throw new ResponseStatusException(BAD_REQUEST, "NPWZ ID is already registered");
            }
            dbRegisterService.createDoctor(
                    request.getEmail(),
                    request.getNationalIdNumber(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getSex(),
                    request.getPassword(),
                    request.getNpwzId()
            );
        } else {
            throw new ResponseStatusException(BAD_REQUEST, "Only patient and doctor registration is enabled");
        }

        return ResponseEntity.ok(Map.of("message", "Account created", "email", request.getEmail()));
    }

    // Called by the frontend when an access token expires.
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> getAccessToken(HttpServletRequest request) {
        return ResponseEntity.ok(authService.getAccessTokenUsingRefreshToken(request));
    }

}



