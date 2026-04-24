package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VisitExaminationListSearchReq {
    @JsonProperty("visitId")
    private Long visitId;
    @JsonProperty("examinationType")
    private String examinationType;
}

