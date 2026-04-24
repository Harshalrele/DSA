package boberbackend.controllers.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CompleteLabExaminationReq {
    @JsonProperty("examinationId")
    private Long examinationId;
    @JsonProperty("result")
    private String result;
}

