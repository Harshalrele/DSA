package boberbackend.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import boberbackend.jpa.model.PhysicalExamination;

import java.util.List;

@Repository
public interface PhysicalExaminationRepository extends JpaRepository<PhysicalExamination, Long> {

}

