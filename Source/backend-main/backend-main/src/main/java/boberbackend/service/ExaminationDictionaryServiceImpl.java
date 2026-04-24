package boberbackend.service;

import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.doctor.ExaminationDictSearchReq;
import boberbackend.enums.BadRequestDictEnum;
import boberbackend.jpa.model.ExaminationDictionary;
import boberbackend.jpa.repository.ExaminationDictionaryRepository;
import boberbackend.jpa.specification.ExaminationDictionarySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminationDictionaryServiceImpl implements ExaminationDictionaryService {

    private final ExaminationDictionaryRepository examinationDictionaryRepository;

    @Override
    public List<ExaminationDictionary> searchExaminationDictionary(ExaminationDictSearchReq req) {
        return examinationDictionaryRepository.findAll(ExaminationDictionarySpecification.searchSpecification(req));
    }

    @Override
    public ExaminationDictionary findByExaminationCode(String code) throws Exception {
        return examinationDictionaryRepository.findByCode(code)
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_CODE, code));
    }
}

