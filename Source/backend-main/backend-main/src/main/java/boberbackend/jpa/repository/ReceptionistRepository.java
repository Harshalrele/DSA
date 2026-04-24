package boberbackend.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import boberbackend.jpa.model.Receptionist;

@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {

}

