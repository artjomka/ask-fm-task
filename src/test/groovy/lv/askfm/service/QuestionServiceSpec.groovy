package lv.askfm.service

import lv.askfm.country.Country
import lv.askfm.country.CountryRepository
import lv.askfm.country.CountryResolverService
import lv.askfm.question.Question
import lv.askfm.question.QuestionRepository
import lv.askfm.question.QuestionService
import lv.askfm.question.validation.QuestionValidationService
import spock.lang.Specification

import java.time.LocalDateTime

class QuestionServiceSpec extends Specification {

  CountryResolverService countryResolverService = Mock()
  QuestionRepository questionRepository = Mock()
  CountryRepository countryRepository = Mock()
  QuestionValidationService questionValidation = Mock()

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
