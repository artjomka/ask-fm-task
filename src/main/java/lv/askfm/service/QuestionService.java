package lv.askfm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lv.askfm.domain.Country;
import lv.askfm.domain.Question;
import lv.askfm.integration.CountryResolverService;
import lv.askfm.repository.QuestionRepository;

@Service
public class QuestionService {

  private final CountryResolverService countryResolverService;
  private final QuestionRepository questionRepository;

  public QuestionService(CountryResolverService countryResolverService, QuestionRepository questionRepository) {
    this.countryResolverService = countryResolverService;
    this.questionRepository = questionRepository;
  }

  @Transactional
  public Question ask(Question question, String ipAddress) {
    final Country country = countryResolverService.getCountry(ipAddress);
    question.setCountry(country);
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
