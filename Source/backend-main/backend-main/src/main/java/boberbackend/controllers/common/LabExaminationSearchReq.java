/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/common/LabExaminationSearchReq.java
 */
package boberbackend.controllers.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LabExaminationSearchReq {
    @JsonProperty("status")
    private String status;
    @JsonProperty("examinationCode")
    private String examinationCode;
    @JsonProperty("labAssistantId")
    private String labAssistantId;
    @JsonProperty("rightsLevel")
    private String rightsLevel;
    @JsonProperty("orderedDataTime")
    private LocalDateTime orderedDateTime;
}



