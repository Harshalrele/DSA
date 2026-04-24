package boberbackend.controllers.supervisor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ApproveLabExaminationReq {
    @JsonProperty("examinationId")
    private Long examinationId;
    @JsonProperty("supervisorNotices")
    private String supervisorNotices;
}

