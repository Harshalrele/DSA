package boberbackend.config.db;

import boberbackend.enums.ExaminationTypeEnum;
import boberbackend.enums.RightsLevelEnum;
import boberbackend.enums.SexEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@EnableScheduling
@Component
@RequiredArgsConstructor
public class PreloadDb {

    private final DbRegisterService registerService;

    private void createDoctors() {
        registerService.createDoctor("doctor1@email.com", "8749321", "John", "Smith", SexEnum.MALE, "doctor1", "npwzId1");
        registerService.createDoctor("doctor2@email.com", "5567810", "Emma", "Johnson", SexEnum.FEMALE, "doctor2", "npwzId2");
        registerService.createDoctor("doctor3@email.com", "1234567", "Michael", "Williams", SexEnum.MALE, "doctor3", "npwzId3");
    }

    private void createPatients() {
        registerService.createPatient("patient1@email.com", "7890123", "Olivia", "Davis", SexEnum.FEMALE, "patient1", "insurancep1");
        registerService.createPatient("patient2@email.com", "2345678", "James", "Miller", SexEnum.MALE, "patient2", "insurancep2");
        registerService.createPatient("patient3@email.com", "8901234", "Isabella", "Wilson", SexEnum.FEMALE, "patient3", "insurancep3");
        registerService.createPatient("patient4@email.com", "4567890", "William", "Moore", SexEnum.MALE, "patient4", "insurancep4");
        registerService.createPatient("patient5@email.com", "6543210", "Ava", "Taylor", SexEnum.FEMALE, "patient5", "insurancep5");

    }

    private void createReceptionists() {
        registerService.createReceptionist("barcic2948@gmail.com", "9876543", "Sophia", "Jones", SexEnum.FEMALE, "receptionist1");
        registerService.createReceptionist("receptionist2@email.com", "3456789", "David", "Brown", SexEnum.MALE, "receptionist2");
    }

    private void createLabAssistants() {
        registerService.createLabAssistant("assistant1@email.com", "9871234", "Benjamin", "Anderson", SexEnum.MALE, "assistant1");
        registerService.createLabAssistant("assistant2@email.com", "5678901", "Mia", "Thomas", SexEnum.FEMALE, "assistant2");
        registerService.createLabAssistant("assistant3@email.com", "3450129", "Alexander", "Jackson", SexEnum.MALE, "assistant3");
    }

    private void createLabSupervisors() {
        registerService.createLabSupervisor("supervisor1@email.com", "8765432", "Charlotte", "White", SexEnum.FEMALE, "supervisor1", RightsLevelEnum.LOW);
        registerService.createLabSupervisor("supervisor2@email.com", "2109876", "Ethan", "Harris", SexEnum.MALE, "supervisor2", RightsLevelEnum.HIGH);
    }

    private void createExaminationDictionary() {
        registerService.createExaminationDictionary("TEMP", "Measure temperature", ExaminationTypeEnum.PHYSICAL, RightsLevelEnum.NONE);
        registerService.createExaminationDictionary("PRES", "Measure pressure", ExaminationTypeEnum.PHYSICAL, RightsLevelEnum.NONE);
        registerService.createExaminationDictionary("WEIG", "Record weight", ExaminationTypeEnum.PHYSICAL, RightsLevelEnum.NONE);
        registerService.createExaminationDictionary("CARDIO", "Cardiovascular assessment", ExaminationTypeEnum.PHYSICAL, RightsLevelEnum.NONE);
        registerService.createExaminationDictionary("NEURO", "Neurological examination", ExaminationTypeEnum.PHYSICAL, RightsLevelEnum.NONE);
        registerService.createExaminationDictionary("STETH", "Use stethoscope", ExaminationTypeEnum.PHYSICAL, RightsLevelEnum.NONE);

        registerService.createExaminationDictionary("XRAY", "Take xray", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.LOW);
        registerService.createExaminationDictionary("BIOP", "Order biopsy", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.MEDIUM);
        registerService.createExaminationDictionary("EKG", "Perform EKG", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.HIGH);
        registerService.createExaminationDictionary("ULTRA", "Perform ultrasound", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.LOW);
        registerService.createExaminationDictionary("BLOOD", "Draw blood", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.MEDIUM);
        registerService.createExaminationDictionary("ECG", "Conduct ECG", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.HIGH);
        registerService.createExaminationDictionary("URINE", "Collect urine sample", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.LOW);
        registerService.createExaminationDictionary("MRI", "Perform MRI scan", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.MEDIUM);
        registerService.createExaminationDictionary("FECAL", "Analyze fecal sample", ExaminationTypeEnum.LABORATORY, RightsLevelEnum.HIGH);
    }

    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void initDatabase() {
        log.info("Preloading database");

        if(registerService.checkIfDataIsPresent()) {
            log.info("Database already preloaded");
            return;
        }

        createPatients();
        createDoctors();
        createReceptionists();
        createLabAssistants();
        createLabSupervisors();
        createExaminationDictionary();
        log.info("Completed preload");
    }

}
