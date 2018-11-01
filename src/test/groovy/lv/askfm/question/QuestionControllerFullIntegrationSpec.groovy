package lv.askfm.question

import lv.askfm.question.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.boot.test.util.TestPropertyValues.of

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = Initializer.class)
class QuestionControllerFullIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Shared
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("question")
            .withUsername("dbuser")
            .withPassword("dbpassword")

    def setupSpec() {
        postgreSQLContainer.start()
    }

    @Transactional
    def "Ask question"() {
        when:
        def responseEntity = restTemplate.postForEntity("/ask", new Question(text: "Do you like pasta?"), Question.class)

        then:
        responseEntity.statusCode == HttpStatus.OK
        responseEntity.getHeaders().get("Content-Type").get(0).contains("application/json")
        responseEntity.getBody()
        responseEntity.getBody().text == "Do you like pasta?"
        responseEntity.getBody().countryCode.equalsIgnoreCase "nl"
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            of("spring.datasource.url=$postgreSQLContainer.jdbcUrl").applyTo configurableApplicationContext.getEnvironment()
        }
    }
}