package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SetVisitStatusReq {
    @JsonProperty("visitId")
    private Long visitId;
    @JsonProperty("visitStatus")
    private String visitStatus;
}

