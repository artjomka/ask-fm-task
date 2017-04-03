package lv.askfm

import lv.askfm.integration.CountryResolverService
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

class IntegrationTestConfiguration {

  private final detachedMockFactory = new DetachedMockFactory()

  @Bean
  CountryResolverService countryResolverService() {
    detachedMockFactory.Mock(CountryResolverService)
  }
}
