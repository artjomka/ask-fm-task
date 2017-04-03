package lv.askfm.integration;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationApiClient {

  private final RestTemplate restTemplate;

  public LocationApiClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
}
