/*
 * Bober Clinic note: Contains code or settings for LabExamination.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/model/LabExamination.java
 */
package boberbackend.jpa.model;

import boberbackend.enums.LaboratoryStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lab_examination")
public class LabExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "doctor_notices")
    private String doctorNotices;

    @Column(name = "supervisor_notices")
    private String supervisorNotices;

    @Column(name = "result")
    private String result;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @NonNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LaboratoryStatusEnum status;

    @NonNull
    @Column(name = "ordered_date", nullable = false)
    private LocalDateTime orderedDateTime;

    @Column(name = "execution_date_time")
    private LocalDateTime executionDateTime; //cancel

    @Column(name = "approval_date_time")
    private LocalDateTime approvalDateTime; //reject

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examination_dictionary", referencedColumnName = "code", nullable = false)
    private ExaminationDictionary examinationDictionary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lab_assist", referencedColumnName = "lab_staff")
    private LabAssistant labAssistant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lab_super", referencedColumnName = "lab_staff")
    private LabSupervisor labSupervisor;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Visit visit;

    public LabExamination(String doctorNotices, ExaminationDictionary examinationDictionary, Visit visit) {
        this.doctorNotices = doctorNotices;
        this.status = LaboratoryStatusEnum.ORDERED;
        this.orderedDateTime = LocalDateTime.now();
        this.examinationDictionary = examinationDictionary;
        this.visit = visit;
    }

}


