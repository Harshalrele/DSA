/*
 * Bober Clinic note: Contains code or settings for LabStaff.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/model/LabStaff.java
 */
package boberbackend.jpa.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "lab_staff")
@AllArgsConstructor
@NoArgsConstructor
public class LabStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_emp_id")
    private long labEmpId;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person", referencedColumnName = "nationalIDNumber", nullable = false)
    private Person person;

    @OneToOne(mappedBy = "labStaff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LabSupervisor supervisor;

    @OneToOne(mappedBy = "labStaff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LabAssistant assistant;

    public LabStaff(@NonNull Person person) {
        this.person = person;
    }

}



