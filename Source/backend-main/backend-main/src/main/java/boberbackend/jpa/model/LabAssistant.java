package boberbackend.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LabAssistant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lab_staff", referencedColumnName = "lab_emp_id", nullable = false)
    @JsonIgnoreProperties({"supervisor", "assistant"})
    private LabStaff labStaff;

    @OneToMany(mappedBy = "labAssistant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LabExamination> labExaminationList;

    public LabAssistant(@NonNull LabStaff labStaff) {
        this.labStaff = labStaff;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabAssistant that = (LabAssistant) o;
        return Objects.equals(getLabStaff(), that.getLabStaff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabStaff());
    }
}

