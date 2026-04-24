/*
 * Bober Clinic note: Contains code or settings for ClinicStaff.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/model/ClinicStaff.java
 */
package boberbackend.jpa.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "clinic_staff")
@AllArgsConstructor
@NoArgsConstructor
public class ClinicStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinic_emp_id")
    private Long clinicEmpId;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person", referencedColumnName = "nationalIDNumber", nullable = false)
    private Person person;

    @OneToOne(mappedBy = "clinicStaff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Doctor doctor;

    @OneToOne(mappedBy = "clinicStaff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Receptionist receptionist;

    public ClinicStaff(@NonNull Person person) {
        this.person = person;
    }

}



