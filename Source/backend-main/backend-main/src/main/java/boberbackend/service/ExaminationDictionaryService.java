package boberbackend.service;

import boberbackend.controllers.doctor.ExaminationDictSearchReq;
import boberbackend.jpa.model.ExaminationDictionary;

import java.util.List;

public interface ExaminationDictionaryService {

    public List<ExaminationDictionary> searchExaminationDictionary(ExaminationDictSearchReq req);

    public ExaminationDictionary findByExaminationCode(String code) throws Exception;

}

