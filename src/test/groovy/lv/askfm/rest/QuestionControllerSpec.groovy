package lv.askfm.rest

import lv.askfm.IntegrationTestConfiguration
import lv.askfm.domain.Country
import lv.askfm.domain.Question
import lv.askfm.integration.CountryResolverService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(IntegrationTestConfiguration)
class QuestionControllerSpec extends Specification {

  @Autowired
  TestRestTemplate restTemplate

  @Autowired
  CountryResolverService countryResolverService

  def "Should save question asked by user and return location"() {
    given:
    1 * countryResolverService.getCountry(_) >> new Country(code: "ru", name: "Russia", limitPerSecond: 5l)

    when:
    def responseEntity = restTemplate.postForEntity("/ask", new Question(text: "Do you like pasta?"), Question.class)

    then:
    responseEntity.statusCode == HttpStatus.OK
    responseEntity.getHeaders().get("Content-Type").get(0).contains("application/json")
    responseEntity.getBody()
    responseEntity.getBody().text == "Do you like pasta?"
    responseEntity.getBody().country.code == "ru".toLowerCase()
  }
}