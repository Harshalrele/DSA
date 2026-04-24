/*
 * Bober Clinic note: Contains code or settings for ExaminationDictionary.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/model/ExaminationDictionary.java
 */
package boberbackend.jpa.model;

import boberbackend.enums.ExaminationTypeEnum;
import boberbackend.enums.RightsLevelEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "examination_dictionary")
public class ExaminationDictionary {

    @Id
    @NonNull
    @Column(name = "code")
    private String code;

    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    @NonNull
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExaminationTypeEnum type;

    @NonNull
    @Column(name = "rights_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private RightsLevelEnum rightsLevel;

    @OneToMany(mappedBy = "examinationDictionary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PhysicalExamination> physicalExaminationList;

    @OneToMany(mappedBy = "examinationDictionary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LabExamination> labExaminationList;

    public ExaminationDictionary(@NonNull String code, @NonNull String description, @NonNull ExaminationTypeEnum type, @NonNull RightsLevelEnum rightsLevel) {
        this.code = code;
        this.description = description;
        this.type = type;
        this.rightsLevel = rightsLevel;
    }

}



