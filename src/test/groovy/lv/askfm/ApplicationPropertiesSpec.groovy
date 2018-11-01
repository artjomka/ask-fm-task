package lv.askfm

import lv.askfm.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.util.TestPropertyValues.of

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = Initializer.class)
class ApplicationPropertiesSpec extends Specification {

  @Autowired
  ApplicationProperties applicationProperties

  @Shared
  static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
          .withDatabaseName("question")
          .withUsername("dbuser")
          .withPassword("dbpassword")

  def setupSpec() {
    postgreSQLContainer.start()
  }

  def "should have properties set"() {
    expect:
    applicationProperties.getDefaultlimit() != null
    applicationProperties.getUrl() != null
    !applicationProperties.badwords.empty
  }

  static class Initializer
          implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      of("spring.datasource.url=$postgreSQLContainer.jdbcUrl").applyTo configurableApplicationContext.getEnvironment()
    }
  }
}
