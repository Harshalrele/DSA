/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/assistant/CompleteLabExaminationReq.java
 */
package boberbackend.controllers.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CompleteLabExaminationReq {
    @JsonProperty("examinationId")
    private Long examinationId;
    @JsonProperty("result")
    private String result;
}



