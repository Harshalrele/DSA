package boberbackend.dtos;

import boberbackend.jpa.model.Person;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitData {
    @JsonProperty("patient")
    private Person assignedPatient;
    @JsonProperty("doctor")
    private Person assignedDoctor;
}

