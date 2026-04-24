package boberbackend.config.db;

import boberbackend.enums.ExaminationTypeEnum;
import boberbackend.enums.RightsLevelEnum;
import boberbackend.enums.RoleEnum;
import boberbackend.enums.SexEnum;
import boberbackend.jpa.model.*;
import boberbackend.jpa.repository.*;
import boberbackend.service.BasicAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbRegisterService {

    private final AppUserRepository appUserRepository;
    private final BasicAuthUserRepository basicAuthUserRepository;
    private final PersonRepository personRepository;

    private final PatientRepository patientRepository;

    private final ClinicStaffRepository clinicStaffRepository;
    private final DoctorRepository doctorRepository;
    private final ReceptionistRepository receptionistRepository;

    private final LabStaffRepository labStaffRepository;
    private final LabSupervisorRepository labSupervisorRepository;
    private final LabAssistantRepository labAssistantRepository;

    private final ExaminationDictionaryRepository examinationDictionaryRepository;

    private AppUser createAppUser(String email, RoleEnum role) {
        return appUserRepository.save(new AppUser(email, role));
    }

    private Person createPersonAndBasicAuth(String email, RoleEnum role, String nationalIdNumber, String firstName, String lastName, SexEnum sex, String password) {
        AppUser appUser = createAppUser(email, role);
        basicAuthUserRepository.save(new BasicAuthUser(appUser, password));
        return personRepository.save(new Person(appUser, nationalIdNumber, firstName, lastName, sex));
    }

    public Patient createPatient(String email, String nationalIdNumber, String firstName, String lastName, SexEnum sex, String password, String insuranceId) {
        return patientRepository.save(new Patient(createPersonAndBasicAuth(email, RoleEnum.PATIENT, nationalIdNumber, firstName, lastName, sex, password), insuranceId));
    }

    private ClinicStaff createClinicStaff(String email, RoleEnum role, String nationalIdNumber, String firstName, String lastName, SexEnum sex, String password) {
        return clinicStaffRepository.save(new ClinicStaff(createPersonAndBasicAuth(email, role, nationalIdNumber, firstName, lastName, sex, password)));
    }

    public Doctor createDoctor(String email, String nationalIdNumber, String firstName, String lastName, SexEnum sex, String password, String npwzId) {
        return doctorRepository.save(new Doctor(createClinicStaff(email, RoleEnum.DOCTOR, nationalIdNumber, firstName, lastName, sex, password), npwzId));
    }

    public Receptionist createReceptionist(String email, String nationalIdNumber, String firstName, String lastName, SexEnum sex, String password) {
        return receptionistRepository.save(new Receptionist(createClinicStaff(email, RoleEnum.RECEPTIONIST, nationalIdNumber, firstName, lastName, sex, password)));
    }

    private LabStaff createLabStaff(String email, RoleEnum role, String nationalIdNumber, String firstName, String lastName, SexEnum sex, String password) {
        return labStaffRepository.save(new LabStaff(createPersonAndBasicAuth(email, role, nationalIdNumber, firstName, lastName, sex, password)));
    }

    public LabAssistant createLabAssistant(String email, String nationalIdNumber, String firstName, String lastName, SexEnum sex, String password) {
        return labAssistantRepository.save(new LabAssistant(createLabStaff(email, RoleEnum.LAB_ASSISTANT, nationalIdNumber, firstName, lastName, sex, password)));
    }

    public LabSupervisor createLabSupervisor(String email, String nationalIdNumber, String firstName, String lastName, SexEnum sex, String password, RightsLevelEnum rightsLevel) {
        return labSupervisorRepository.save(new LabSupervisor(createLabStaff(email, RoleEnum.LAB_SUPER, nationalIdNumber, firstName, lastName, sex, password), rightsLevel));
    }

    public ExaminationDictionary createExaminationDictionary(String code, String description, ExaminationTypeEnum type, RightsLevelEnum rightsLevel) {
        return examinationDictionaryRepository.save(new ExaminationDictionary(code, description, type, rightsLevel));
    }

    public boolean checkIfDataIsPresent() {
        return doctorRepository.findById(1L).isPresent();
    }

}

