/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/assistant/CancelLabExaminationReq.java
 */
package boberbackend.controllers.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CancelLabExaminationReq {
    @JsonProperty("examinationId")
    private Long examinationId;
    @JsonProperty("cancellationReason")
    private String cancellationReason;
}



