package lv.askfm.country;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lv.askfm.ApplicationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    if (countryResponse.getCountryCode().isEmpty()) {
      return createDefaultCountry();
    }

    Country country = new Country();
    country.setName(countryResponse.getCountryName());
    country.setCode(countryResponse.getCountryCode());
    country.setLimitPerSecond(applicationProperties.getDefaultlimit());
    return country;
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
