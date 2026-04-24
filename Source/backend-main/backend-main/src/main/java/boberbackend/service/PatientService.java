/*
 * Bober Clinic note: Defines the methods that this service must provide.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/PatientService.java
 */
package boberbackend.service;

import boberbackend.controllers.receptionist.PatientCreateReq;
import boberbackend.controllers.receptionist.PatientSearchReq;
import boberbackend.jpa.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> searchPatients(PatientSearchReq req);

    Patient findByPatientInsuranceId(String insuranceNumber);

    Patient createPatient(PatientCreateReq req);

}



