/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/receptionist/VisitCreateReq.java
 */
package boberbackend.controllers.receptionist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VisitCreateReq {
    @JsonProperty("doctorNpwzId")
    private String doctorNpwzId;
    @JsonProperty("patientInsuranceId")
    private String patientInsuranceId;
    @JsonProperty("scheduledDateTime")
    private String scheduledDateTime;
}



