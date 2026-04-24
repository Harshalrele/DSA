package boberbackend.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "receptionist")
@AllArgsConstructor
@NoArgsConstructor
public class Receptionist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_staff", referencedColumnName = "clinic_emp_id", nullable = false)
    @JsonIgnoreProperties({"doctor", "receptionist"})
    private ClinicStaff clinicStaff;

    @OneToMany(mappedBy = "receptionist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Visit> visitList;

    public Receptionist(@NonNull ClinicStaff clinicStaff) {
        this.clinicStaff = clinicStaff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receptionist that = (Receptionist) o;
        return Objects.equals(getClinicStaff(), that.getClinicStaff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClinicStaff());
    }
}
