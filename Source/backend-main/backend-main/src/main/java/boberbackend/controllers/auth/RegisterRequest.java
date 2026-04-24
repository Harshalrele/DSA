package boberbackend.controllers.auth;

import boberbackend.enums.RoleEnum;
import boberbackend.enums.SexEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// DTO sent from the frontend Register page to the backend /register endpoint.
public class RegisterRequest {

    // Email becomes the login username.
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nationalIdNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private SexEnum sex;

    @NotNull
    private RoleEnum role;

    // Required only when role is PATIENT.
    private String insuranceId;

    // Required only when role is DOCTOR.
    private String npwzId;
}



