/*
 * Bober Clinic note: Defines fixed allowed values used by the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/enums/BadRequestDictEnum.java
 */
package boberbackend.enums;

import lombok.Getter;

@Getter
public enum BadRequestDictEnum {

    //General
    METHOD_NOT_ALLOWED("Methode not allowed. Change to GET/POST"),
    BAD_AUTH("Bad auth attempt"),

    //Login
    BAD_LOGIN("Bad credentials"),
    BAD_OAUTH2_LOGIN("Bad oAuth2 Login"),

    //JWT
    EXPIRED_JWT("JWT has expired"),

    //User
    BAD_USER("Could not find user in DB"),
    BAD_RECEPTIONIST_ID("Could not find receptionist - Internal backend problem, contact author"),
    BAD_DOCTOR_NPWZ_ID("Could not find doctor with specified npwz ID"),
    BAD_PATIENT_INSURANCE_ID("Could not find patient with specified insurance ID"),
    MISSING_ASSISTANT("Could not find assistant in examination, examination not confirmed by assistant"),
    BAD_ASSISTANT_ID("Could not find assistant - Internal backend problem, contact author"),
    BAD_SUPERVISOR_ID("Could not find supervisor - Internal backend problem, contact author"),

    //Parse
    BAD_DATE("Could not parse date"),
    BAD_VALUE("Could not parse value"),

    //Visit
    NO_VISIT_ID("Missing visitId parameter"),
    BAD_VISIT_ID("Could not find visit by id"),
    BAD_VISIT_STATUS("Could not accept provided visit status"),

    //Examination
    BAD_EXAMINATION_CODE("Could not accept provided examination code"),
    BAD_P_EXAMINATION_RESULT("Could not accept provided physical examination result"),
    BAD_LAB_EXAMINATION_DOCTOR_NOTICE("Could not accept provided doctor notice"),
    BAD_EXAMINATION_TYPE("Could not accept provided examination type"),
    BAD_LAB_EXAMINATION_ID("Could not find examination with provided id"),
    BAD_LAB_EXAMINATION_RESULT("Could not accept provided result"),
    BAD_LAB_EXAMINATION_STATUS("Could not parse provided status"),
    BAD_LAB_EXAMINATION_RIGHTS_LEVEL("Could not parse provided rights level"),

    //Registering users
    NO_EMAIL("Missing email in request"),
    NO_NATIONAL_ID("Missing national id number in request"),
    NO_FIRST_NAME("Missing first name in request"),
    NO_LAST_NAME("Missing last name in request"),
    NO_SEX("https://www.youtube.com/watch?v=UHmFbT8DPX8"),
    NO_PASSWORD("WHAT? Why is there a password for a patient???"), //I am so fucking dumb...
    // We are completing a project and I just now realised that we treat a patient the same as any other user.
    // Just assume we can upgrade the app to allow patients to log in as well. You can actually login as patient and it would work
    NO_INSURANCE_ID("Missing insurance Id in request"),

    ;

    private final String description;

    private BadRequestDictEnum(String description) {
        this.description = description;
    }

}



