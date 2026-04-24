package boberbackend.jpa.repository;

import boberbackend.jpa.model.AppUser;
import boberbackend.jpa.model.BasicAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasicAuthUserRepository extends JpaRepository<BasicAuthUser, Long> {
    public Optional<BasicAuthUser> findByUser(AppUser user);
}

