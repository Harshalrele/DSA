/*
 * Bober Clinic note: Carries data between backend layers or API responses.
 * File: backend-main/backend-main/src/main/java/boberbackend/dtos/VisitData.java
 */
package boberbackend.dtos;

import boberbackend.jpa.model.Person;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitData {
    @JsonProperty("patient")
    private Person assignedPatient;
    @JsonProperty("doctor")
    private Person assignedDoctor;
}



