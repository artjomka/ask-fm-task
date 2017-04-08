package lv.askfm.rest

import lv.askfm.domain.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT


@SpringBootTest(webEnvironment = RANDOM_PORT)
class QuestionControllerFullIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Transactional
    def "Ask question"() {
        when:
        def responseEntity = restTemplate.postForEntity("/ask", new Question(text: "Do you like pasta?"), Question.class)

        then:
        responseEntity.statusCode == HttpStatus.OK
        responseEntity.getHeaders().get("Content-Type").get(0).contains("application/json")
        responseEntity.getBody()
        responseEntity.getBody().text == "Do you like pasta?"
        responseEntity.getBody().countryCode.equalsIgnoreCase "lv"
    }

}