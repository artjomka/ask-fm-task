package lv.askfm.question.validation

import lv.askfm.ApplicationProperties
import lv.askfm.question.Question
import spock.lang.Specification

class BadWordsCheckSpec extends Specification {

  ApplicationProperties applicationProperties = Mock()


  def "should stop question with bad word"() {
    given:
    applicationProperties.getBadwords() >> ["badword1", "badword2"]
    def badWordValidation = new BadWordsCheck(applicationProperties)

    when:
    badWordValidation.validate(new Question(text: " Hello wiht badword1"))

    then:
    BadWordFound exception = thrown()
    exception.message == "Bad word detected in question "
  }

  def " question without bad word"() {
    given:
    applicationProperties.getBadwords() >> ["badword1"]
    def badWordsCheck = new BadWordsCheck(applicationProperties)

    when:
    badWordsCheck.validate(new Question(text: "Innocent question"))

    then:
    noExceptionThrown()
  }
}
