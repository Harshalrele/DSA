package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VisitDetailsReq {

    @JsonProperty("visitId")
    private Long visitId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("diagnostics")
    private String diagnostics;

}

