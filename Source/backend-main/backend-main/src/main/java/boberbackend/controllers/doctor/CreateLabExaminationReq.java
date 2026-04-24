package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateLabExaminationReq {
    @JsonProperty("visitId")
    private Long visitId;
    @JsonProperty("doctorNotices")
    private String doctorNotices;
    @JsonProperty("examinationCode")
    private String examinationCode;
}

