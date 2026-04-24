/*
 * Bober Clinic note: Connects this part of the app to the database using Spring Data JPA.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/repository/ExaminationDictionaryRepository.java
 */
package boberbackend.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import boberbackend.jpa.model.ExaminationDictionary;

import java.util.Optional;

@Repository
public interface ExaminationDictionaryRepository extends JpaRepository<ExaminationDictionary, Long>, JpaSpecificationExecutor<ExaminationDictionary> {

    Optional<ExaminationDictionary> findByCode(String code);

}



