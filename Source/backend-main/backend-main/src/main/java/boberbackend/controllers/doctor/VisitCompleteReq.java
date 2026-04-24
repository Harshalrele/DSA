package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VisitCompleteReq {
    @JsonProperty("visitId")
    private Long visitId;
    @JsonProperty("diagnostics")
    private String diagnostics;
}

