/*
 * Bober Clinic note: Contains the main business logic for this service.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/DoctorServiceImpl.java
 */
package boberbackend.service;

import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.doctor.CreateLabExaminationReq;
import boberbackend.controllers.doctor.CreatePhysicalExaminationReq;
import boberbackend.controllers.doctor.VisitExaminationListSearchReq;
import boberbackend.controllers.doctor.VisitExaminationSearchRes;
import boberbackend.controllers.receptionist.DoctorSearchReq;
import boberbackend.enums.BadRequestDictEnum;
import boberbackend.enums.ExaminationTypeEnum;
import boberbackend.jpa.model.*;
import boberbackend.jpa.repository.*;
import boberbackend.jpa.specification.DoctorSpecification;
import boberbackend.utils.BeaverUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ExaminationDictionaryRepository examinationDictionaryRepository;
    private final VisitRepository visitRepository;
    private final PhysicalExaminationRepository physicalExaminationRepository;
    private final LabExaminationRepository labExaminationRepository;

    @Override
    public List<Doctor> searchDoctors(DoctorSearchReq req) {
        return doctorRepository.findAll(DoctorSpecification.searchSpecification(req));
    }

    @Override
    public Doctor findByNpwzId(String npwzId) {
        return doctorRepository.findByNpwzId(npwzId).orElse(null);
    }

    @Override
    public PhysicalExamination createPhysicalExamination(CreatePhysicalExaminationReq req) throws BadRequestException {

        if (req.getVisitId() == null) throw new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, null);
        if (req.getExaminationDictCode() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_CODE, null);
        if (req.getExaminationDateTime() == null) throw new BadRequestException(BadRequestDictEnum.BAD_DATE, null);
        if (req.getResult() == null) throw new BadRequestException(BadRequestDictEnum.BAD_P_EXAMINATION_RESULT, null);

        Visit visit = visitRepository.findById(req.getVisitId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, req.getVisitId().toString()));
        ExaminationDictionary examinationDict = examinationDictionaryRepository
                .findByCode(req.getExaminationDictCode())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_CODE, req.getExaminationDictCode()));

        LocalDateTime examinationDateTime;
        try {
            examinationDateTime = BeaverUtils.convertReqToDateTime(req.getExaminationDateTime());
        } catch (DateTimeParseException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_DATE, req.getExaminationDateTime());
        }

        PhysicalExamination newExamination = new PhysicalExamination(req.getResult(), examinationDict, examinationDateTime, visit);
        return physicalExaminationRepository.save(newExamination);
    }

    @Override
    public List<PhysicalExamination> getVistPhysicalExaminationList(Long visitId) throws BadRequestException {
        if (visitId == null) throw new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, null);
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, visitId.toString()))
                .getPhysicalExaminationList();
    }

    @Override
    public LabExamination createLabExamination(CreateLabExaminationReq req) throws BadRequestException {

        if (req.getVisitId() == null) throw new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, null);
        if (req.getExaminationCode() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_CODE, null);
        if (req.getDoctorNotices() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_DOCTOR_NOTICE, null);

        Visit visit = visitRepository.findById(req.getVisitId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, req.getVisitId().toString()));
        ExaminationDictionary examinationDictionary = examinationDictionaryRepository.findByCode(req.getExaminationCode())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_CODE, req.getExaminationCode()));

        if (examinationDictionary.getType() != ExaminationTypeEnum.LABORATORY)
            throw new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_CODE, examinationDictionary.getType().toString());

        LabExamination labExamination = new LabExamination(req.getDoctorNotices(), examinationDictionary, visit);

        return labExaminationRepository.save(labExamination);
    }

    @Override
    public List<LabExamination> getVistLabExaminationList(Long visitId) throws BadRequestException {
        if (visitId == null) throw new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, null);
        return visitRepository.findById(visitId).orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, visitId.toString())).getLabExaminationList();
    }

    @Override
    public VisitExaminationSearchRes getVisitExaminationList(VisitExaminationListSearchReq req) throws BadRequestException {
        if (req.getVisitId() == null) throw new BadRequestException(BadRequestDictEnum.BAD_VISIT_ID, null);
        if (req.getExaminationType() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_TYPE, null);

        try {
            if (Objects.equals(req.getExaminationType(), "BOTH")) {
                return new VisitExaminationSearchRes(getVistPhysicalExaminationList(req.getVisitId()),
                        getVistLabExaminationList(req.getVisitId()));
            } else if (ExaminationTypeEnum.valueOf(req.getExaminationType()) == ExaminationTypeEnum.PHYSICAL) {
                return new VisitExaminationSearchRes(getVistPhysicalExaminationList(req.getVisitId()), Collections.emptyList());
            } else if (ExaminationTypeEnum.valueOf(req.getExaminationType()) == ExaminationTypeEnum.LABORATORY) {
                return new VisitExaminationSearchRes(Collections.emptyList(), getVistLabExaminationList(req.getVisitId()));
            } else {
                throw new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_TYPE, req.getExaminationType());
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_TYPE, req.getExaminationType());
        }
    }
}



