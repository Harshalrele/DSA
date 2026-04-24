/*
 * Bober Clinic note: Contains code or settings for ExaminationDictionarySpecification.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/specification/ExaminationDictionarySpecification.java
 */
package boberbackend.jpa.specification;

import boberbackend.controllers.doctor.ExaminationDictSearchReq;
import boberbackend.jpa.model.ExaminationDictionary;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExaminationDictionarySpecification {

    public static Specification<ExaminationDictionary> searchSpecification(ExaminationDictSearchReq req) {
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (req.getCode() != null && !req.getCode().isEmpty())
                predicates.add(builder.and(builder.equal(root.get("code"), req.getCode())));
            if (req.getDescription() != null && !req.getDescription().isEmpty())
                predicates.add(builder.and(builder.like(root.get("description"), "%" + req.getDescription() + "%")));

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

}



