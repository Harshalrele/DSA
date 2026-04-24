package boberbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatientSearchData {
    private String firstName;
    private String lastName;
    private String insuranceId;
}

