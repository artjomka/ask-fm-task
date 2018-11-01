package lv.askfm.rest

import lv.askfm.IntegrationTestConfiguration
import lv.askfm.country.Country
import lv.askfm.country.CountryRepository
import lv.askfm.country.CountryResolverService
import lv.askfm.question.Question
import lv.askfm.question.QuestionRepository
import lv.askfm.question.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.AfterTransaction
import org.springframework.test.context.transaction.BeforeTransaction
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.boot.test.util.TestPropertyValues.of

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(IntegrationTestConfiguration)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = Initializer.class)
class QuestionControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    CountryRepository countryRepository

    @Autowired
    QuestionService questionService

    CountryResolverService countryResolverService = Mock()

    @Shared
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("question")
            .withUsername("dbuser")
            .withPassword("dbpassword")

    def setupSpec() {
        postgreSQLContainer.start()
    }

    def setup() {
        questionService.countryResolverService = countryResolverService
    }

    @BeforeTransaction
    def "Populate database data "() {
        def latvia = new Country(code: "lv", name: "Latvia", limitPerSecond: 5)
        def russia = new Country(code: "ru", name: "Russia", limitPerSecond: 5)
        countryRepository.save(latvia)
        countryRepository.save(russia)
        questionRepository.save(new Question(text: "How are you world?", countryCode: latvia.code, created: LocalDateTime.now()))
        questionRepository.save(new Question(text: "Second question world?", countryCode: latvia.code, created: LocalDateTime.now()))
        questionRepository.save(new Question(text: "How do you do? ", countryCode: russia.code, created: LocalDateTime.now()))
    }


    @Transactional
    def "Should save question asked by user and return location"() {
        when:
        def responseEntity = restTemplate.postForEntity("/ask", new Question(text: "Do you like pasta?"), Question.class)

        then:
        countryResolverService.getCountry(_) >> new Country(code: "RU", name: "Russia", limitPerSecond: 5l)
        responseEntity.statusCode == HttpStatus.OK
        responseEntity.getHeaders().get("Content-Type").get(0).contains("application/json")
        responseEntity.getBody()
        responseEntity.getBody().text == "Do you like pasta?"
        responseEntity.getBody().countryCode.equalsIgnoreCase "ru"
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

    @AfterTransaction
    def "cleanup tables"() {
        countryRepository.deleteAll()
        questionRepository.deleteAll()
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            of("spring.datasource.url=$postgreSQLContainer.jdbcUrl").applyTo configurableApplicationContext.getEnvironment()
        }
    }
}