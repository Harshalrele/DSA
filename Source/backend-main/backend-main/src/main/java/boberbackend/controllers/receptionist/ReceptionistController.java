/*
 * Bober Clinic note: Receives HTTP requests from the frontend for this feature.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/receptionist/ReceptionistController.java
 */
package boberbackend.controllers.receptionist;

import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.common.VisitSearchReq;
import boberbackend.controllers.common.VisitSearchRes;
import boberbackend.enums.BadRequestDictEnum;
import boberbackend.enums.VisitStatusEnum;
import boberbackend.jpa.model.Doctor;
import boberbackend.jpa.model.Patient;
import boberbackend.jpa.model.Visit;
import boberbackend.service.DoctorService;
import boberbackend.service.PatientService;
import boberbackend.service.VisitService;
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
@RequestMapping("/api/receptionist")
@RequiredArgsConstructor
public class ReceptionistController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final VisitService visitService;

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @GetMapping("/search-patient")
    public ResponseEntity<List<Patient>> searchPatient(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "nationalIdNumber", required = false) String nationalIdNumber,
            @RequestParam(value = "insuranceId", required = false) String insuranceId) {
        PatientSearchReq req = new PatientSearchReq(firstName, lastName, nationalIdNumber, insuranceId);
        return ResponseEntity.ok(patientService.searchPatients(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @PostMapping("/create-patient")
    public ResponseEntity<?> createPatient(@RequestBody PatientCreateReq req) {
        return ResponseEntity.ok(patientService.createPatient(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @GetMapping("/search-doctor")
    public ResponseEntity<List<Doctor>> searchDoctor(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "nationalIdNumber", required = false) String nationalIdNumber,
            @RequestParam(value = "npwzId", required = false) String npwzId) {

        DoctorSearchReq req = new DoctorSearchReq(firstName, lastName, nationalIdNumber, npwzId);
        return ResponseEntity.ok(doctorService.searchDoctors(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @PostMapping("/create-visit")
    public ResponseEntity<Visit> scheduleVisit(@RequestBody VisitCreateReq req) {
        return ResponseEntity.ok(visitService.createNewVisit(req));
    }

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @GetMapping("/search-visit")
    public ResponseEntity<List<VisitSearchRes>> searchVisit(
            @RequestParam(required = false) String patientFirstName,
            @RequestParam(required = false) String patientLastName,
            @RequestParam(required = false) String patientInsuranceId,
            @RequestParam(required = false) String doctorFirstName,
            @RequestParam(required = false) String doctorLastName,
            @RequestParam(required = false) String doctorNpwzId,
            @RequestParam(required = false) VisitStatusEnum status,
            @RequestParam(required = false) String scheduledDate) {

        LocalDateTime scheduledDateTime = null;

        try {
            if(scheduledDate != null) {
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

    @PreAuthorize("hasAuthority('SCOPE_RECEPTIONIST')")
    @PostMapping("/cancel-visit")
    public ResponseEntity<Visit> cancelVisit(@RequestBody VisitCancelReq req) {
        return ResponseEntity.ok(visitService.cancelVisit(req.getVisitId()));
    }
}



