package boberbackend.service;

import boberbackend.controllers.assistant.CancelLabExaminationReq;
import boberbackend.controllers.assistant.CompleteLabExaminationReq;
import boberbackend.controllers.common.LabExaminationSearchReq;
import boberbackend.controllers.supervisor.ApproveLabExaminationReq;
import boberbackend.controllers.supervisor.RejectLabExaminationReq;
import boberbackend.jpa.model.LabExamination;

import java.util.List;

public interface LabExaminationService {

    public List<LabExamination> assistantSearchLabExamination(LabExaminationSearchReq req);

    public List<LabExamination> supervisorSearchLabExamination(LabExaminationSearchReq req);

    public LabExamination getLabExaminationById(Long examinationId);

    public LabExamination cancelLabExamination(CancelLabExaminationReq req);

    public LabExamination completeLabExamination(CompleteLabExaminationReq req);

    public LabExamination approveLabExamination(ApproveLabExaminationReq req);

    public LabExamination rejectLabExamination(RejectLabExaminationReq req);

    public List<LabExamination> supervisorEasySearchLabExamination();
}

