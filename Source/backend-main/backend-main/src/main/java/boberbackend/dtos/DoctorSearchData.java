/*
 * Bober Clinic note: Carries data between backend layers or API responses.
 * File: backend-main/backend-main/src/main/java/boberbackend/dtos/DoctorSearchData.java
 */
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



