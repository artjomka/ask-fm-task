package lv.askfm.service

import lv.askfm.domain.Country
import lv.askfm.domain.Question
import lv.askfm.integration.CountryResolverService
import lv.askfm.repository.CountryRepository
import lv.askfm.repository.QuestionRepository
import lv.askfm.validation.QuestionValidation
import spock.lang.Specification

import java.time.LocalDateTime

class QuestionServiceSpec extends Specification {

  CountryResolverService countryResolverService = Mock()
  QuestionRepository questionRepository = Mock()
  CountryRepository countryRepository = Mock()
  QuestionValidation questionValidation = Mock()

  def " should return question with country "() {
    given:
    def questionService = new QuestionService(countryResolverService, questionRepository, countryRepository, questionValidation)
    def created = LocalDateTime.now()
    when:
    def result = questionService.ask(new Question(text: "Hello ", created: created), '127.0.0.1', created)
    then:
    1 * countryResolverService.getCountry(_) >> new Country(name: "Latvia", code: "lv", limitPerSecond: 5)
    1 * questionRepository.save(_)

    result.countryCode == 'lv'
    result.text == 'Hello '
    result.created == created
  }

}
