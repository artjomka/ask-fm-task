package lv.askfm.rest

import lv.askfm.IntegrationTestConfiguration
import lv.askfm.domain.Country
import lv.askfm.domain.Question
import lv.askfm.integration.CountryResolverService
import lv.askfm.repository.CountryRepository
import lv.askfm.repository.QuestionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.transaction.BeforeTransaction
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(IntegrationTestConfiguration)
@DirtiesContext
class QuestionControllerSpec extends Specification {

  @Autowired
  TestRestTemplate restTemplate

  @Autowired
  QuestionRepository questionRepository

  @Autowired
  CountryRepository countryRepository

  @Autowired
  CountryResolverService countryResolverService

  def setup() {
    countryResolverService.getCountry(_) >> new Country(code: "ru", name: "Russia", limitPerSecond: 5l)
  }

  def "Should save question asked by user and return location"() {
    when:
    def responseEntity = restTemplate.postForEntity("/ask", new Question(text: "Do you like pasta?"), Question.class)

    then:
    responseEntity.statusCode == HttpStatus.OK
    responseEntity.getHeaders().get("Content-Type").get(0).contains("application/json")
    responseEntity.getBody()
    responseEntity.getBody().text == "Do you like pasta?"
    responseEntity.getBody().country.code == "ru".toLowerCase()
  }


  @BeforeTransaction
  def populateQuestionTable() {
    def latvia = new Country(code: "lv", name: "Latvia", limitPerSecond: 5)
    def russia = new Country(code: "ru", name: "Russia", limitPerSecond: 5)
    countryRepository.save(latvia)
    countryRepository.save(russia)
    questionRepository.save(new Question(text: "How are you world?", country: latvia))
    questionRepository.save(new Question(text: "Second question world?", country: latvia))
    questionRepository.save(new Question(text: "How do you do? ", country: russia))
  }

  @Transactional
  def "Should fetch questions by country code"() {

    when:
    def responseEntity = restTemplate.getForEntity("/find?countryCode=lv", List)
    then:
    responseEntity.statusCode == HttpStatus.OK
    responseEntity.getHeaders().get("Content-Type").get(0).contains("application/json")
    responseEntity.getBody().size() == 2
  }

  @Transactional
  def "Should fetch all questions"() {
    when:
    def responseEntity = restTemplate.getForEntity("/find", List)
    then:
    responseEntity.statusCode == HttpStatus.OK
    responseEntity.getHeaders().get("Content-Type").get(0).contains("application/json")
    responseEntity.getBody().size() == 3
  }
}