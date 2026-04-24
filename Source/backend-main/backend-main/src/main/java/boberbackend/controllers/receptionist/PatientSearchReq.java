package boberbackend.controllers.receptionist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientSearchReq {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("nationalIdNumber")
    private String nationalIdNumber;
    @JsonProperty("insuranceId")
    private String insuranceId;
}

