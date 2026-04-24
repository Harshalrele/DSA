/*
 * Bober Clinic note: Defines the methods that this service must provide.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/ExaminationDictionaryService.java
 */
package boberbackend.service;

import boberbackend.controllers.doctor.ExaminationDictSearchReq;
import boberbackend.jpa.model.ExaminationDictionary;

import java.util.List;

public interface ExaminationDictionaryService {

    public List<ExaminationDictionary> searchExaminationDictionary(ExaminationDictSearchReq req);

    public ExaminationDictionary findByExaminationCode(String code) throws Exception;

}



