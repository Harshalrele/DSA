package boberbackend.controllers.common;

import boberbackend.jpa.model.Doctor;
import boberbackend.jpa.model.Patient;
import boberbackend.jpa.model.Visit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class VisitSearchRes {
    @JsonProperty("visit")
    @JsonIgnoreProperties({"patient", "selectedDoctor", "receptionist"})
    private Visit visit;
    @JsonProperty("selectedPatient")
    @JsonIgnoreProperties({"visitList"})
    private Patient patient;
    @JsonProperty("selectedDoctor")
    @JsonIgnoreProperties({"visitList"})
    private Doctor doctor;
}

