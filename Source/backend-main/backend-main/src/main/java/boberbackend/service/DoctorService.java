package boberbackend.service;

import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.doctor.CreateLabExaminationReq;
import boberbackend.controllers.doctor.CreatePhysicalExaminationReq;
import boberbackend.controllers.doctor.VisitExaminationListSearchReq;
import boberbackend.controllers.doctor.VisitExaminationSearchRes;
import boberbackend.controllers.receptionist.DoctorSearchReq;
import boberbackend.jpa.model.Doctor;
import boberbackend.jpa.model.LabExamination;
import boberbackend.jpa.model.PhysicalExamination;

import java.util.List;

public interface DoctorService {
    List<Doctor> searchDoctors(DoctorSearchReq req);

    Doctor findByNpwzId(String npwzId);

    PhysicalExamination createPhysicalExamination(CreatePhysicalExaminationReq req) throws BadRequestException;

    List<PhysicalExamination> getVistPhysicalExaminationList(Long visitId) throws BadRequestException;

    LabExamination createLabExamination(CreateLabExaminationReq req) throws BadRequestException;

    List<LabExamination> getVistLabExaminationList(Long visitId) throws BadRequestException;

    VisitExaminationSearchRes getVisitExaminationList(VisitExaminationListSearchReq req) throws BadRequestException;
}

