package boberbackend.controllers.receptionist;

import boberbackend.enums.SexEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PatientCreateReq {
    @JsonProperty("email")
    private String email;
    @JsonProperty("nationalIdNumber")
    private String nationalIdNumber;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("sex")
    private SexEnum sex;
    @JsonProperty("insuranceId")
    private String insuranceId;
}

