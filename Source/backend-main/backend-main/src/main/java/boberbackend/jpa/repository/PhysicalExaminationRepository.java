/*
 * Bober Clinic note: Connects this part of the app to the database using Spring Data JPA.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/repository/PhysicalExaminationRepository.java
 */
package boberbackend.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import boberbackend.jpa.model.PhysicalExamination;

import java.util.List;

@Repository
public interface PhysicalExaminationRepository extends JpaRepository<PhysicalExamination, Long> {

}



