/*
 * Bober Clinic note: Contains code or settings for Doctor.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/model/Doctor.java
 */
package boberbackend.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "npwz_id", nullable = false)
    private String npwzId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_staff", referencedColumnName = "clinic_emp_id", nullable = false)
    @JsonIgnoreProperties({"doctor", "receptionist"})
    private ClinicStaff clinicStaff;

    @JsonIgnore
    @OneToMany(mappedBy = "selectedDoctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Visit> visitList;

    public Doctor(@NonNull ClinicStaff clinicStaff, @NonNull String npwzId) {
        this.clinicStaff = clinicStaff;
        this.npwzId = npwzId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor that = (Doctor) o;
        return Objects.equals(getClinicStaff(), that.getClinicStaff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClinicStaff());
    }
}



