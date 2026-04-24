/*
 * Bober Clinic note: Receives HTTP requests from the frontend for this feature.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/doctor/DoctorController.java
 */
package boberbackend.controllers.doctor;

import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.common.VisitSearchReq;
import boberbackend.controllers.common.VisitSearchRes;
import boberbackend.enums.BadRequestDictEnum;
import boberbackend.jpa.model.Visit;
import boberbackend.service.DoctorService;
import boberbackend.service.ExaminationDictionaryService;
import boberbackend.service.VisitService;
import boberbackend.enums.VisitStatusEnum;
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
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final VisitService visitService;
    private final ExaminationDictionaryService examinationDictionaryService;

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @GetMapping("/search-visit")
    public ResponseEntity<List<VisitSearchRes>> searchVisit(
            @RequestParam(value = "patientFirstName", required = false) String patientFirstName,
            @RequestParam(value = "patientLastName", required = false) String patientLastName,
            @RequestParam(value = "patientInsuranceId", required = false) String patientInsuranceId,
            @RequestParam(value = "doctorFirstName", required = false) String doctorFirstName,
            @RequestParam(value = "doctorLastName", required = false) String doctorLastName,
            @RequestParam(value = "doctorNpwzId", required = false) String doctorNpwzId,
            @RequestParam(value = "status", required = false) VisitStatusEnum status,
            @RequestParam(value = "scheduledDate", required = false) String scheduledDate) {

        LocalDateTime scheduledDateTime = null;

        try {
            if (scheduledDate != null) {
                scheduledDateTime = BeaverUtils.convertReqToDateTime(scheduledDate);
            }
        } catch (DateTimeException e) {
            throw new BadRequestException(BadRequestDictEnum.BAD_DATE, scheduledDate);
        }

        VisitSearchReq req = new VisitSearchReq(patientFirstName, patientLastName, patientInsuranceId, doctorFirstName,
                doctorLastName, doctorNpwzId, status, scheduledDateTime);
        List<Visit> searchResult = visitService.searchVisits(req);
        List<VisitSearchRes> result = searchResult.stream()
                .map(visit -> new VisitSearchRes(visit, visit.getPatient(), visit.getSelectedDoctor()))
                .toList();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @GetMapping("/get-visit/{visitId}")
    public ResponseEntity<VisitSearchRes> getVisitById(@PathVariable Long visitId) {
        Visit visit = visitService.getVisitById(visitId);
        VisitSearchRes response = new VisitSearchRes(visit, visit.getPatient(), visit.getSelectedDoctor());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @PostMapping("/set-details")
    public ResponseEntity<?> setDetails(@RequestBody VisitDetailsReq req) {
        Visit visit = visitService.setVisitDetails(req);
        return ResponseEntity.ok(visit);
    }

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @GetMapping("/search-examination")
    public ResponseEntity<?> searchExamination(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "description", required = false) String description) {

        ExaminationDictSearchReq req = new ExaminationDictSearchReq(code, description);
        return ResponseEntity.ok(examinationDictionaryService.searchExaminationDictionary(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @GetMapping("/search-visit-examination")
    public ResponseEntity<?> getExaminationForVisit(
            @RequestParam(value = "visitId") Long visitId,
            @RequestParam(value = "examinationType", required = false) String examinationType) {

        VisitExaminationListSearchReq req = new VisitExaminationListSearchReq(visitId, examinationType);
        return ResponseEntity.ok(doctorService.getVisitExaminationList(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @PostMapping("/add-physical-examination")
    public ResponseEntity<?> addPhysicalExamination(@RequestBody CreatePhysicalExaminationReq req) {
        return ResponseEntity.ok(doctorService.createPhysicalExamination(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @PostMapping("/add-lab-examination")
    public ResponseEntity<?> addLabExamination(@RequestBody CreateLabExaminationReq req) {
        return ResponseEntity.ok(doctorService.createLabExamination(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @PostMapping("/set-visit-status")
    public ResponseEntity<?> setVisitStatus(@RequestBody SetVisitStatusReq req) {
        return ResponseEntity.ok(visitService.setVisitStatus(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_DOCTOR')")
    @PostMapping("/complete-visit")
    public ResponseEntity<?> completeVisit(@RequestBody VisitCompleteReq req) {
        Visit visit = visitService.completeVisit(req);
        return ResponseEntity.ok(visit);
    }

}



