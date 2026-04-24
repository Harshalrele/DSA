package boberbackend.controllers.receptionist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VisitCancelReq {
    @JsonProperty("visitId")
    private Long visitId;
}

//TODO: Consider changing to String
