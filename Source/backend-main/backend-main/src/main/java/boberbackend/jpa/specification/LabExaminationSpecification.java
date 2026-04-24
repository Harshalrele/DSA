package boberbackend.jpa.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import boberbackend.enums.LaboratoryStatusEnum;
import boberbackend.enums.RightsLevelEnum;
import boberbackend.jpa.model.ExaminationDictionary;
import boberbackend.jpa.model.LabExamination;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LabExaminationSpecification {

    public static Specification<LabExamination> searchSpecification(LaboratoryStatusEnum status, RightsLevelEnum rightsLevel, Long assistantId, String examinationCode, LocalDateTime orderedDateTime) {
        return ((root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (examinationCode != null && !examinationCode.isEmpty())
                predicates.add(builder.and(builder.equal(root.get("examinationDictionary").get("code"), examinationCode)));
            if (rightsLevel != null)
                predicates.add(builder.and(builder.equal(root.get("examinationDictionary").get("rightsLevel"), rightsLevel)));
            if (status != null)
                predicates.add(builder.and(builder.equal(root.get("status"), status)));
            if (assistantId != null) {
                predicates.add(builder.and(builder.equal(root.get("labAssistant").get("id"), assistantId)));
            }
            if (orderedDateTime != null) {

                LocalDate scheduledDate = LocalDate.from(orderedDateTime);
                LocalDateTime startOfDay = scheduledDate.atStartOfDay();
                LocalDateTime endOfDay = startOfDay.plusDays(1);

                Predicate greaterOrEqual = builder.greaterThanOrEqualTo(root.get("orderedDateTime"), startOfDay);
                Predicate lessThen = builder.lessThan(root.get("orderedDateTime"), endOfDay);
                predicates.add(builder.and(greaterOrEqual, lessThen));
            }

            query.orderBy(builder.asc(root.get("orderedDateTime")));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<LabExamination> hasRightsLevels(List<RightsLevelEnum> rightsLevels) {
        return (Root<LabExamination> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Join<LabExamination, ExaminationDictionary> examinationDictionaryJoin = root.join("examinationDictionary");
            return examinationDictionaryJoin.get("rightsLevel").in(rightsLevels);
        };
    }
}

