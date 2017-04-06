package lv.askfm.integration;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lv.askfm.config.ApplicationProperties;
import lv.askfm.domain.Country;
import lv.askfm.repository.CountryRepository;

@Service
public class CountryResolverService {

  public static final String DEFAULT_COUNTRY_NAME = "Latvia";
  public static final String DEFAULT_COUNTRY_CODE = "lv";
  private final ApplicationProperties applicationProperties;
  private final RestTemplate restTemplate;
  private final CountryRepository countryRepository;

  public CountryResolverService(ApplicationProperties applicationProperties, RestTemplate restTemplate, CountryRepository countryRepository) {
    this.applicationProperties = applicationProperties;
    this.restTemplate = restTemplate;
    this.countryRepository = countryRepository;
  }


  public Country getCountry(String ipAddress) {
    CountryResponse countryResponse = getCountryByIp(ipAddress);
    return countryRepository.findByCode(countryResponse.getCountryCode())
        .orElse(createCountryFromResponse(countryResponse));
  }

  private Country createCountryFromResponse(CountryResponse countryResponse) {
    Country country = new Country();
    country.setName(countryResponse.getCountryName());
    country.setLimitPerSecond(applicationProperties.getDefaultlimit());
    return null;
  }

  @HystrixCommand(fallbackMethod = "defaultCountry")
  private CountryResponse getCountryByIp(String ipAddress) {

    return restTemplate.getForEntity(applicationProperties.getUrl(), CountryResponse.class, ipAddress).getBody();
  }

  public Country defaultCountry() {
    return countryRepository.findByCode(DEFAULT_COUNTRY_CODE)
        .orElse(createDefaultCountry());
  }

  private Country createDefaultCountry() {
    final Country latvia = new Country();
    latvia.setCode(DEFAULT_COUNTRY_CODE);
    latvia.setName(DEFAULT_COUNTRY_NAME);
    latvia.setLimitPerSecond(applicationProperties.getDefaultlimit());
    return latvia;
  }
}
