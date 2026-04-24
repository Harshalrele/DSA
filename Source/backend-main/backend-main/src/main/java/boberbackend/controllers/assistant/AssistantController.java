package boberbackend.controllers.assistant;

import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.common.LabExaminationSearchReq;
import boberbackend.enums.BadRequestDictEnum;
import boberbackend.jpa.model.LabExamination;
import boberbackend.service.LabExaminationService;
import boberbackend.utils.BeaverUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/assistant")
@RequiredArgsConstructor
public class AssistantController {

    private final LabExaminationService labExaminationService;

    @PreAuthorize("hasAuthority('SCOPE_LAB_ASSISTANT')")
    @PostMapping("/complete-examination")
    public ResponseEntity<LabExamination> completeLabExamination(@RequestBody CompleteLabExaminationReq req) {
        return ResponseEntity.ok(labExaminationService.completeLabExamination(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_LAB_ASSISTANT')")
    @PostMapping("/cancel-examination")
    public ResponseEntity<LabExamination> cancelLabExamination(@RequestBody CancelLabExaminationReq req) {
        return ResponseEntity.ok(labExaminationService.cancelLabExamination(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_LAB_ASSISTANT')")
    @GetMapping("/get-examination/{examinationId}")
    public ResponseEntity<LabExamination> getLabExaminationById(@PathVariable Long examinationId) {
        return ResponseEntity.ok(labExaminationService.getLabExaminationById(examinationId));
    }

    @PreAuthorize("hasAuthority('SCOPE_LAB_ASSISTANT')")
    @GetMapping("/search-examination")
    public ResponseEntity<List<LabExamination>> searchLabExamination(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "examinationCode", required = false) String examinationCode,
            @RequestParam(value = "labAssistantId", required = false) String labAssistantId,
            @RequestParam(value = "rightsLevel", required = false) String rightsLevel,
            @RequestParam(value = "orderedDateTime", required = false) String orderedDate)
    {

        LocalDateTime orderedDateTime = null;

        try {
            if (orderedDate != null) {
                orderedDateTime = BeaverUtils.convertReqToDateTime(orderedDate);
            }
        } catch (DateTimeException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_DATE, orderedDate);
        }

        LabExaminationSearchReq req = new LabExaminationSearchReq(status, examinationCode, labAssistantId, rightsLevel, orderedDateTime);
        return ResponseEntity.ok(labExaminationService.assistantSearchLabExamination(req));
    }

}

