package boberbackend.jpa.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import boberbackend.controllers.receptionist.PatientSearchReq;
import boberbackend.jpa.model.Patient;
import boberbackend.jpa.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PatientSpecification {

    public static Specification<Patient> searchSpecification(PatientSearchReq req) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<Patient, Person> personJoin = root.join("person", JoinType.INNER);

            if (req.getFirstName() != null && !req.getFirstName().isEmpty())
                predicates.add(builder.and(builder.like(personJoin.get("firstName"), "%" + req.getFirstName() + "%")));
            if (req.getLastName() != null && !req.getLastName().isEmpty())
                predicates.add(builder.and(builder.like(personJoin.get("lastName"), "%" + req.getLastName() + "%")));
            if (req.getNationalIdNumber() != null && !req.getNationalIdNumber().isEmpty())
                predicates.add(builder.and(builder.equal(personJoin.get("nationalIdNumber"), req.getNationalIdNumber())));
            if (req.getInsuranceId() != null && !req.getInsuranceId().isEmpty())
                predicates.add(builder.and(builder.equal(root.get("insuranceId"), req.getInsuranceId())));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

