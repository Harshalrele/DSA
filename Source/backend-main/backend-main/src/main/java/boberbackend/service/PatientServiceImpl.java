/*
 * Bober Clinic note: Contains the main business logic for this service.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/PatientServiceImpl.java
 */
package boberbackend.service;

import boberbackend.config.db.DbRegisterService;
import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.receptionist.PatientCreateReq;
import boberbackend.controllers.receptionist.PatientSearchReq;
import boberbackend.enums.BadRequestDictEnum;
import boberbackend.jpa.model.Patient;
import boberbackend.jpa.repository.PatientRepository;
import boberbackend.jpa.specification.PatientSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DbRegisterService dbRegisterService;

    @Override
    public List<Patient> searchPatients(PatientSearchReq req) {
        return patientRepository.findAll(PatientSpecification.searchSpecification(req));
    }

    @Override
    public Patient findByPatientInsuranceId(String insuranceId) {
        return patientRepository.findByInsuranceId(insuranceId).orElse(null);
    }

    @Override
    public Patient createPatient(PatientCreateReq req) {
        if (req.getEmail() == null)
            throw new BadRequestException(BadRequestDictEnum.NO_EMAIL, null);
        if (req.getNationalIdNumber() == null)
            throw new BadRequestException(BadRequestDictEnum.NO_NATIONAL_ID, null);
        if (req.getFirstName() == null)
            throw new BadRequestException(BadRequestDictEnum.NO_FIRST_NAME, null);
        if (req.getLastName() == null)
            throw new BadRequestException(BadRequestDictEnum.NO_LAST_NAME, null);
        if (req.getSex() == null)
            throw new BadRequestException(BadRequestDictEnum.NO_SEX, null);
        if (req.getInsuranceId() == null)
            throw new BadRequestException(BadRequestDictEnum.NO_INSURANCE_ID, null);

        return dbRegisterService.createPatient(req.getEmail(), req.getNationalIdNumber(), req.getFirstName(), req.getLastName(), req.getSex(), "ABCDEFGHIJKLMNOPQ", req.getInsuranceId());

    }
}



