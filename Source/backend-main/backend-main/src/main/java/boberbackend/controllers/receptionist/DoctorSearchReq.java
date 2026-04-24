package boberbackend.controllers.receptionist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoctorSearchReq {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("nationalIdNumber")
    private String nationalIdNumber;
    @JsonProperty("npwzId")
    private String npwzId;
}

