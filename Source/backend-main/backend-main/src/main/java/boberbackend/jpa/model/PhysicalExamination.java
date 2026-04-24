package boberbackend.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "physical_examination")
public class PhysicalExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "result")
    private String result;

    @NonNull
    @Column(name = "examinationDateTime")
    private LocalDateTime examinationDateTime;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examination_dictionary", referencedColumnName = "code")
    private ExaminationDictionary examinationDictionary;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit", referencedColumnName = "id")
    @JsonIgnore
    private Visit visit;

    public PhysicalExamination(@NonNull String result, @NonNull ExaminationDictionary examinationDictionary, @NonNull LocalDateTime examinationDateTime, @NonNull Visit visit) {
        this.result = result;
        this.examinationDictionary = examinationDictionary;
        this.examinationDateTime = examinationDateTime;
        this.visit = visit;
    }
}

