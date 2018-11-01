package lv.askfm.question.validation

import lv.askfm.country.Country
import lv.askfm.question.QuestionRepository
import spock.lang.Specification

import java.time.LocalDateTime

class QuestionLimitCheckSpec extends Specification {

  QuestionRepository questionRepository = Mock()

  def "question is within amount limits"() {
    given:
    def questionLimitCheck = new QuestionLimitCheck(questionRepository)

    when:
    questionLimitCheck.validateQuestionsLimit(new Country(code: "lv", limitPerSecond: 5), LocalDateTime.now())

    then:
    1 * questionRepository.countByCreatedBetweenAndCountryCode(_, _, "lv") >> 5L
    noExceptionThrown()
  }

  def "Too many question per second"() {
    given:
    def questionLimitCheck = new QuestionLimitCheck(questionRepository)

    when:
    questionLimitCheck.validateQuestionsLimit(new Country(code: "lv", limitPerSecond: 5, name: "Latvia"), LocalDateTime.now())

    then:
    1 * questionRepository.countByCreatedBetweenAndCountryCode(_, _, "lv") >> 6L
    QuestionAmountLimitExceeded exception = thrown()
    exception.message == "Questions amount 5 per second exceeded for country Latvia; country code lv"
  }
}
