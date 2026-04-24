/*
 * Bober Clinic note: Describes request data sent from the frontend to the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/receptionist/VisitCancelReq.java
 */
package boberbackend.controllers.receptionist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VisitCancelReq {
    @JsonProperty("visitId")
    private Long visitId;
}

//TODO: Consider changing to String


