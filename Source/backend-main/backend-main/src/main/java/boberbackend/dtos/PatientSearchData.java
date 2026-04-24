/*
 * Bober Clinic note: Carries data between backend layers or API responses.
 * File: backend-main/backend-main/src/main/java/boberbackend/dtos/PatientSearchData.java
 */
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



