/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/supervisor/ApproveLabExaminationReq.java
 */
package boberbackend.controllers.supervisor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ApproveLabExaminationReq {
    @JsonProperty("examinationId")
    private Long examinationId;
    @JsonProperty("supervisorNotices")
    private String supervisorNotices;
}



