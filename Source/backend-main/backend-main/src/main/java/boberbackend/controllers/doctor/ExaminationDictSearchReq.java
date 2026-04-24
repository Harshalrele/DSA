/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/doctor/ExaminationDictSearchReq.java
 */
package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExaminationDictSearchReq {
    @JsonProperty("code")
    private String code;
    @JsonProperty("description")
    private String description;
}



