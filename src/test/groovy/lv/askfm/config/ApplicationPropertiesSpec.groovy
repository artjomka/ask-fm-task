package lv.askfm.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ApplicationPropertiesSpec extends Specification {

  @Autowired
  ApplicationProperties applicationProperties

  def "should have properties set"() {
    expect:
    applicationProperties.getDefaultlimit() != null
    applicationProperties.getUrl() != null
    !applicationProperties.badwords.empty
  }
}
