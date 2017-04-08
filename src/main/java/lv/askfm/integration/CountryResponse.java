package lv.askfm.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryResponse {


  @JsonProperty(value = "country_code")
  private String countryCode;

  @JsonProperty(value = "country_name")
  private String countryName;
}
