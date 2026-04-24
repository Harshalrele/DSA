package boberbackend.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import boberbackend.jpa.model.LabExamination;

@Repository
public interface LabExaminationRepository extends JpaRepository<LabExamination, Long>, JpaSpecificationExecutor<LabExamination> {

}

