package boberbackend.controllers.common;

import boberbackend.enums.VisitStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VisitSearchReq {
    @JsonProperty("patientFirstName")
    private String patientFirstName;
    @JsonProperty("patientLastName")
    private String patientLastName;
    @JsonProperty("patientInsuranceId")
    private String patientInsuranceId;

    @JsonProperty("doctorFirstName")
    private String doctorFirstName;
    @JsonProperty("doctorLastName")
    private String doctorLastName;
    @JsonProperty("doctorNpwzId")
    private String doctorNpwzId;

    @JsonProperty("status")
    private VisitStatusEnum status;
    @JsonProperty("scheduledDate")
    private LocalDateTime scheduledDate;
}

