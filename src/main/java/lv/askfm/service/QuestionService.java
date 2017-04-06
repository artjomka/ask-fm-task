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

@Service
@Setter
public class QuestionService {

  private CountryResolverService countryResolverService;
  private final QuestionRepository questionRepository;
  private final CountryRepository countryRepository;

  public QuestionService(CountryResolverService countryResolverService, QuestionRepository questionRepository, CountryRepository countryRepository) {
    this.countryResolverService = countryResolverService;
    this.questionRepository = questionRepository;
    this.countryRepository = countryRepository;
  }

  @Transactional
  public Question ask(Question question, String ipAddress) {
    final Country country = countryResolverService.getCountry(ipAddress);
    countryRepository.save(country);
    question.setCountryCode(country.getCode());
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
