package lv.askfm.rest

import lv.askfm.domain.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class QuestionControllerSpecification extends Specification {

  @Autowired
  TestRestTemplate restTemplate


  def "Should save question asked by user and return location"() {
    when:
    def responseEntity = restTemplate.postForEntity("/ask", new Question(text: "Do you like pasta?"), Question.class)

    then:
    responseEntity.statusCode == HttpStatus.OK
    responseEntity.getHeaders().containsKey("Location")
    responseEntity.getBody()
    responseEntity.getBody().text == "Do you like pasta?"
    responseEntity.getBody().country.code == "".toLowerCase()
  }
}