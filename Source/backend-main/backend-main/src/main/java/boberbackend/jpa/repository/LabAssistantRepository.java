package boberbackend.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import boberbackend.jpa.model.LabAssistant;

@Repository
public interface LabAssistantRepository extends JpaRepository<LabAssistant, Long> {

}

