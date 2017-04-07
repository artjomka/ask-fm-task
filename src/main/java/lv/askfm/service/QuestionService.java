package lv.askfm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;
import lv.askfm.domain.Country;
import lv.askfm.domain.Question;
import lv.askfm.integration.CountryResolverService;
import lv.askfm.repository.CountryRepository;
import lv.askfm.repository.QuestionRepository;
import lv.askfm.validation.QuestionValidation;

@Service
@Setter
public class QuestionService {

  private CountryResolverService countryResolverService;
  private final QuestionRepository questionRepository;
  private final CountryRepository countryRepository;
  private final QuestionValidation questionValidation;

  public QuestionService(CountryResolverService countryResolverService, QuestionRepository questionRepository,
      CountryRepository countryRepository, QuestionValidation questionValidation) {
    this.countryResolverService = countryResolverService;
    this.questionRepository = questionRepository;
    this.countryRepository = countryRepository;
    this.questionValidation = questionValidation;
  }

  @Transactional
  public Question ask(Question question, String ipAddress) {
    final Country country = countryResolverService.getCountry(ipAddress);
    question.setCountryCode(country.getCode());
    questionValidation.validate(question);

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
