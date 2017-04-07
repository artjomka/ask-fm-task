package lv.askfm.repository

import lv.askfm.domain.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.transaction.BeforeTransaction
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDateTime


@DataJpaTest
class QuestionRepositorySpec extends Specification {

  @Autowired
  QuestionRepository questionRepository


  @BeforeTransaction
  def "Prepopulate database data"() {
    questionRepository.save(new Question(text: "hi", countryCode: "lv",
            created: LocalDateTime.of(2017, 04, 07, 10, 10, 10, 20000000)))
    questionRepository.save(new Question(text: "hi2", countryCode: "lv",
            created: LocalDateTime.of(2017, 04, 07, 10, 10, 10, 30000000)))
    questionRepository.save(new Question(text: "hi4", countryCode: "lv",
            created: LocalDateTime.of(2017, 04, 07, 10, 10, 10, 40000000)))
    questionRepository.save(new Question(text: "hiru1", countryCode: "ru",
            created: LocalDateTime.of(2017, 04, 07, 10, 10, 10)))
    questionRepository.save(new Question(text: "hiru2", countryCode: "ru",
            created: LocalDateTime.of(2017, 04, 07, 10, 10, 10)))
  }

  @Transactional
  def "should get correct amount of questions in period of time"() {
    given:
    def startDate = LocalDateTime.of(2017, 04, 07, 10, 10, 10, 25000000)
    def endDate = LocalDateTime.of(2017, 04, 07, 10, 10, 10, 40000000)
    when:
    def amount = questionRepository.countByCreatedBetweenAndCountryCode(startDate, endDate, "lv")

    then:
    amount == 2
  }

  @Transactional
  def "should get correct amount of questions by country"() {
    when:
    def result = questionRepository.findByCountryCode("ru")
    then:
    result.size() == 2
    result.each {
      it.countryCode == "ru"
    }
  }
}
