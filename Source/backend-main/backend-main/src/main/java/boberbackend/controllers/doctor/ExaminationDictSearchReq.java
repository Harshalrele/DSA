package boberbackend.controllers.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExaminationDictSearchReq {
    @JsonProperty("code")
    private String code;
    @JsonProperty("description")
    private String description;
}

