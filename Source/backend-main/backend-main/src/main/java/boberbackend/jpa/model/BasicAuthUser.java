/*
 * Bober Clinic note: Contains code or settings for BasicAuthUser.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/model/BasicAuthUser.java
 */
package boberbackend.jpa.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "basic_auth_user")
@NoArgsConstructor
@AllArgsConstructor
public class BasicAuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user", referencedColumnName = "id", nullable = false)
    private AppUser user;

    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

    public BasicAuthUser(@NonNull AppUser appUser, @NonNull String password) {
        this.user = appUser;
        this.password = password;
    }
}



