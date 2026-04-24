/*
 * Bober Clinic note: Contains code or settings for Person.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/model/Person.java
 */
package boberbackend.jpa.model;

import boberbackend.enums.SexEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @Column(nullable = false, unique = true)
    @OrderBy("nationalIDNumber ASC")
    private String nationalIDNumber;

    @NonNull
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NonNull
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @NonNull
    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private AppUser user;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Patient patient;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private LabStaff labStaff;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private ClinicStaff clinicStaff;

    public Person(@NonNull AppUser appUser, @NonNull String nationalIDNumber, @NonNull String firstName, @NonNull String lastName, @NonNull SexEnum sex) {
        this.user = appUser;
        this.nationalIDNumber = nationalIDNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
    }

}


