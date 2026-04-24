package boberbackend.jpa.model;

import boberbackend.enums.VisitStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "visit")
@NoArgsConstructor
@AllArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "diagnostics")
    private String diagnostics;

    @NonNull
    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDateTime;

    @NonNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private VisitStatusEnum visitStatus;

    @NonNull
    @JoinColumn(name = "receptionist", referencedColumnName = "clinic_staff", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"visitList"})
    private Receptionist receptionist;

    @NonNull
    @JoinColumn(name = "selected_doctor", referencedColumnName = "clinic_staff", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"visitList"})
    private Doctor selectedDoctor;

    @NonNull
    @JoinColumn(name = "patient", referencedColumnName = "insurance_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"visitList"})
    private Patient patient;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LabExamination> labExaminationList;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhysicalExamination> physicalExaminationList;

    public Visit(@NonNull Receptionist receptionist, @NonNull Doctor selectedDoctor, @NonNull Patient patient, @NonNull LocalDateTime scheduledDateTime) {
        this.receptionist = receptionist;
        this.selectedDoctor = selectedDoctor;
        this.patient = patient;
        this.visitStatus = VisitStatusEnum.REGISTERED;
        this.scheduledDateTime = scheduledDateTime;
    }

}

