package boberbackend.jpa.model;

import boberbackend.enums.JwtTokenTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "token", unique = true, nullable = false, length = 10000)
    private String token;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user", referencedColumnName = "id", nullable = false)
    private AppUser user;
}

