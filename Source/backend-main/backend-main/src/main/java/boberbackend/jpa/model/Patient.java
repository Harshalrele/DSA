/*
 * Bober Clinic note: Contains code or settings for Patient.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/model/Patient.java
 */
package boberbackend.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @Column(name = "insurance_id")
    private String insuranceId;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person", referencedColumnName = "nationalIDNumber", nullable = false)
    private Person person;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Visit> visitList;

    public Patient(@NonNull Person person, String insuranceId) {
        this.person = person;
        this.insuranceId = insuranceId;
    }
}



