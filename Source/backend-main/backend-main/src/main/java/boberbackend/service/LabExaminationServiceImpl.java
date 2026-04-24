/*
 * Bober Clinic note: Contains the main business logic for this service.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/LabExaminationServiceImpl.java
 */
package boberbackend.service;

import boberbackend.controllers.assistant.CancelLabExaminationReq;
import boberbackend.controllers.assistant.CompleteLabExaminationReq;
import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.common.LabExaminationSearchReq;
import boberbackend.controllers.supervisor.ApproveLabExaminationReq;
import boberbackend.controllers.supervisor.RejectLabExaminationReq;
import boberbackend.enums.BadRequestDictEnum;
import boberbackend.enums.LaboratoryStatusEnum;
import boberbackend.enums.RightsLevelEnum;
import boberbackend.jpa.model.AppUser;
import boberbackend.jpa.model.LabAssistant;
import boberbackend.jpa.model.LabExamination;
import boberbackend.jpa.model.LabSupervisor;
import boberbackend.jpa.repository.AppUserRepository;
import boberbackend.jpa.repository.LabExaminationRepository;
import boberbackend.jpa.specification.LabExaminationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabExaminationServiceImpl implements LabExaminationService {

    private final LabExaminationRepository labExaminationRepository;
    private final AppUserRepository appUserRepository;


    @Override
    public List<LabExamination> assistantSearchLabExamination(LabExaminationSearchReq req) {

        LaboratoryStatusEnum status;

        try {
            status = LaboratoryStatusEnum.valueOf(req.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_STATUS, e.getMessage());
        } catch (NullPointerException e) {
            status = null;
        }

        return labExaminationRepository.findAll(LabExaminationSpecification.searchSpecification(status, null, null, req.getExaminationCode(), req.getOrderedDateTime()));
    }

    @Override
    public List<LabExamination> supervisorSearchLabExamination(LabExaminationSearchReq req) {
        LaboratoryStatusEnum status;

        try {
            status = LaboratoryStatusEnum.valueOf(req.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_STATUS, e.getMessage());
        } catch (NullPointerException e) {
            status = null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));

        LabSupervisor labSupervisor = appUser.getPerson().getLabStaff().getSupervisor();

        return labExaminationRepository.findAll(LabExaminationSpecification.searchSpecification(status, null, null, req.getExaminationCode(), req.getOrderedDateTime()));
    }

    @Override
    public LabExamination getLabExaminationById(Long examinationId) throws BadRequestException {

        return labExaminationRepository.findById(examinationId).orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, examinationId.toString()));
    }

    @Override
    public LabExamination cancelLabExamination(CancelLabExaminationReq req) {

        if (req.getExaminationId() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, null);

        LabExamination examination = labExaminationRepository.findById(req.getExaminationId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, req.getExaminationId().toString()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));
        LabAssistant labAssistant = appUser.getPerson().getLabStaff().getAssistant();

        examination.setLabAssistant(labAssistant);
        examination.setCancellationReason(req.getCancellationReason());
        examination.setExecutionDateTime(LocalDateTime.now());
        examination.setStatus(LaboratoryStatusEnum.CANCELLED);

        return labExaminationRepository.save(examination);
    }

    @Override
    public LabExamination completeLabExamination(CompleteLabExaminationReq req) {
        if (req.getExaminationId() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, null);

        if (req.getResult() == null || req.getResult().isEmpty()) {
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_RESULT, null);
        }

        LabExamination examination = labExaminationRepository.findById(req.getExaminationId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, req.getExaminationId().toString()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));
        LabAssistant labAssistant = appUser.getPerson().getLabStaff().getAssistant();

        examination.setLabAssistant(labAssistant);
        examination.setResult(req.getResult());
        examination.setExecutionDateTime(LocalDateTime.now());
        examination.setStatus(LaboratoryStatusEnum.COMPLETED);

        return labExaminationRepository.save(examination);
    }

    @Override
    public LabExamination approveLabExamination(ApproveLabExaminationReq req) {
        if (req.getExaminationId() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, null);

        LabExamination examination = labExaminationRepository.findById(req.getExaminationId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, req.getExaminationId().toString()));

        if (examination.getLabAssistant() == null)
            throw new BadRequestException(BadRequestDictEnum.MISSING_ASSISTANT, null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));

        LabSupervisor labSupervisor = appUser.getPerson().getLabStaff().getSupervisor();

        examination.setLabSupervisor(labSupervisor);
        examination.setSupervisorNotices(req.getSupervisorNotices());
        examination.setApprovalDateTime(LocalDateTime.now());
        examination.setStatus(LaboratoryStatusEnum.APPROVED);

        return labExaminationRepository.save(examination);
    }

    @Override
    public LabExamination rejectLabExamination(RejectLabExaminationReq req) {
        if (req.getExaminationId() == null)
            throw new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, null);

        LabExamination examination = labExaminationRepository.findById(req.getExaminationId())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_LAB_EXAMINATION_ID, req.getExaminationId().toString()));

        if (examination.getLabAssistant() == null)
            throw new BadRequestException(BadRequestDictEnum.MISSING_ASSISTANT, null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_ASSISTANT_ID, authentication.getName()));

        LabSupervisor labSupervisor = appUser.getPerson().getLabStaff().getSupervisor();

        examination.setLabSupervisor(labSupervisor);
        examination.setSupervisorNotices(req.getSupervisorNotices());
        examination.setApprovalDateTime(LocalDateTime.now());
        examination.setStatus(LaboratoryStatusEnum.REJECTED);

        return labExaminationRepository.save(examination);
    }

    @Override
    public List<LabExamination> supervisorEasySearchLabExamination() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AppUser appUser = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_RECEPTIONIST_ID, authentication.getName()));
        LabSupervisor labSupervisor = appUser.getPerson().getLabStaff().getSupervisor();

        RightsLevelEnum rightsLevelEnum = labSupervisor.getRightsLevel();


        List<LabExamination> examinations = new ArrayList<>();

        switch (rightsLevelEnum) {
            case LOW:
                examinations.addAll(labExaminationRepository.findAll(
                        LabExaminationSpecification.hasRightsLevels(Collections.singletonList(RightsLevelEnum.LOW))));
                break;
            case MEDIUM:
                examinations.addAll(labExaminationRepository.findAll(
                        LabExaminationSpecification.hasRightsLevels(List.of(RightsLevelEnum.LOW, RightsLevelEnum.MEDIUM))));
                break;
            case HIGH:
                examinations.addAll(labExaminationRepository.findAll(
                        LabExaminationSpecification.hasRightsLevels(List.of(RightsLevelEnum.LOW, RightsLevelEnum.MEDIUM, RightsLevelEnum.HIGH))));
                break;
        }
        return examinations;

    }
}



