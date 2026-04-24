package boberbackend.jpa.repository;

import boberbackend.jpa.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT t FROM RefreshToken t WHERE t.user.id = :userId AND t.revoked = false")
    public List<RefreshToken> findActiveTokenByUserId(@Param("userId") Long userId);

    public Optional<RefreshToken> findByToken(String token);
}

