package lv.askfm.service;

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
}
