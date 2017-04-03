package lv.askfm.service

import lv.askfm.domain.Country
import lv.askfm.domain.Question
import lv.askfm.integration.CountryResolverService
import lv.askfm.repository.QuestionRepository
import spock.lang.Specification

class QuestionServiceSpec extends Specification {

  CountryResolverService countryResolverService = Mock()
  QuestionRepository questionRepository = Mock()


  def " should return question with country "() {
    given:
    def questionService = new QuestionService(countryResolverService, questionRepository)
    when:
    def result = questionService.ask(new Question(text: "Hello "), '127.0.0.1')
    then:
    1 * countryResolverService.getCountry(_) >> new Country(name: "Latvia", code: "lv", limitPerSecond: 5)
    1 * questionRepository.save(_)

    result.country.code == 'lv'
    result.country.name == 'Latvia'
    result.country.limitPerSecond == 5

  }
}
