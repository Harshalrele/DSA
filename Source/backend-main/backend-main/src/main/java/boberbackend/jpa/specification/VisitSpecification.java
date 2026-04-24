/*
 * Bober Clinic note: Contains code or settings for VisitSpecification.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/jpa/specification/VisitSpecification.java
 */
package boberbackend.jpa.specification;

import boberbackend.controllers.common.VisitSearchReq;
import boberbackend.jpa.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class VisitSpecification {
    public static Specification<Visit> searchSpecification(VisitSearchReq req) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<Visit, Doctor> doctorJoin = root.join("selectedDoctor", JoinType.INNER);
            Join<Doctor, ClinicStaff> staffJoin = doctorJoin.join("clinicStaff", JoinType.INNER);
            Join<ClinicStaff, Person> personDoctorJoin = staffJoin.join("person", JoinType.INNER);
            Join<Visit, Patient> patientJoin = root.join("patient", JoinType.INNER);
            Join<Patient, Person> personPatientJoin = patientJoin.join("person", JoinType.INNER);

            if (req.getPatientFirstName() != null && !req.getPatientFirstName().isEmpty())
                predicates.add(builder.and(builder.like(personPatientJoin.get("firstName"), "%" + req.getPatientFirstName() + "%")));
            if (req.getPatientLastName() != null && !req.getPatientLastName().isEmpty())
                predicates.add(builder.and(builder.like(personPatientJoin.get("lastName"), "%" + req.getPatientLastName() + "%")));
            if (req.getPatientInsuranceId() != null && !req.getPatientInsuranceId().isEmpty())
                predicates.add(builder.and(builder.equal(patientJoin.get("insuranceId"), req.getPatientInsuranceId())));

            if (req.getDoctorFirstName() != null && !req.getDoctorFirstName().isEmpty())
                predicates.add(builder.and(builder.like(personDoctorJoin.get("firstName"), "%" + req.getDoctorFirstName() + "%")));
            if (req.getDoctorLastName() != null && !req.getDoctorLastName().isEmpty())
                predicates.add(builder.and(builder.like(personDoctorJoin.get("lastName"), "%" + req.getDoctorLastName() + "%")));
            if (req.getDoctorNpwzId() != null && !req.getDoctorNpwzId().isEmpty())
                predicates.add(builder.and(builder.equal(root.get("npwzId"), req.getDoctorNpwzId())));

            if (req.getScheduledDate() != null) {

                LocalDate scheduledDate = LocalDate.from(req.getScheduledDate());
                LocalDateTime startOfDay = scheduledDate.atStartOfDay();
                LocalDateTime endOfDay = startOfDay.plusDays(1);

                Predicate greaterOrEqual = builder.greaterThanOrEqualTo(root.get("scheduledDateTime"), startOfDay);
                Predicate lessThen = builder.lessThan(root.get("scheduledDateTime"), endOfDay);
                predicates.add(builder.and(greaterOrEqual, lessThen));
            }

            if (req.getStatus() != null)
                predicates.add(builder.and(builder.equal(root.get("visitStatus"), req.getStatus())));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}



