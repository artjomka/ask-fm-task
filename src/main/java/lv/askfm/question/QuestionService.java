package lv.askfm.question;

import lombok.Setter;
import lv.askfm.country.Country;
import lv.askfm.country.CountryRepository;
import lv.askfm.country.CountryResolverService;
import lv.askfm.question.validation.QuestionValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Setter
public class QuestionService {

    private CountryResolverService countryResolverService;
    private final QuestionRepository questionRepository;
    private final CountryRepository countryRepository;
    private final QuestionValidationService questionValidation;

    public QuestionService(CountryResolverService countryResolverService, QuestionRepository questionRepository,
                           CountryRepository countryRepository, QuestionValidationService questionValidation) {
        this.countryResolverService = countryResolverService;
        this.questionRepository = questionRepository;
        this.countryRepository = countryRepository;
        this.questionValidation = questionValidation;
    }

    @Transactional
    public Question ask(Question question, String ipAddress, LocalDateTime creationDate) {
        final Country country = countryResolverService.getCountry(ipAddress);
        question.setCountryCode(country.getCode());
        question.setCreated(creationDate);
        questionValidation.validate(question, country);
        countryRepository.save(country);
        questionRepository.save(question);
        return question;
    }

    public List<Question> findQuestionsByCountryCode(String countryCode) {
        return questionRepository.findByCountryCode(countryCode);
    }

    public List<Question> findAll() {
        List<Question> result = new ArrayList<>();
        questionRepository.findAll().forEach(result::add);
        return result;
    }
}
