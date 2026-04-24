package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreatePhysicalExaminationReq {
    @JsonProperty("result")
    private String result;
    @JsonProperty("examinationDictCode")
    private String examinationDictCode;
    @JsonProperty("visitId")
    private Long visitId;
    @JsonProperty("examinationDateTime")
    private String examinationDateTime;
}

