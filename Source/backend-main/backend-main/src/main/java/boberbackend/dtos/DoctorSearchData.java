package boberbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorSearchData {
    private String firstName;
    private String lastName;
    private String npwzId;
}

