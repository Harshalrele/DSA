package boberbackend.jpa.model;

import boberbackend.enums.RightsLevelEnum;
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
@Table(name = "lab_supervisor")
public class LabSupervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "rightsLevel", nullable = false)
    @Enumerated(EnumType.STRING)
    private RightsLevelEnum rightsLevel;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lab_staff", referencedColumnName = "lab_emp_id", nullable = false)
    @JsonIgnoreProperties({"supervisor", "assistant"})
    private LabStaff labStaff;

    @OneToMany(mappedBy = "labSupervisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LabExamination> labExaminationList;

    public LabSupervisor(@NonNull LabStaff labStaff, @NonNull RightsLevelEnum rightsLevel) {
        this.labStaff = labStaff;
        this.rightsLevel = rightsLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabSupervisor that = (LabSupervisor) o;
        return Objects.equals(getLabStaff(), that.getLabStaff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabStaff());
    }
}

