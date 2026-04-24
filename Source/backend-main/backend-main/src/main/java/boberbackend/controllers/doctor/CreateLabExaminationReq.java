/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/doctor/CreateLabExaminationReq.java
 */
package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateLabExaminationReq {
    @JsonProperty("visitId")
    private Long visitId;
    @JsonProperty("doctorNotices")
    private String doctorNotices;
    @JsonProperty("examinationCode")
    private String examinationCode;
}



