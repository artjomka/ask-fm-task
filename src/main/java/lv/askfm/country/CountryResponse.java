package lv.askfm.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryResponse {

  private String countryCode;

  @JsonProperty(value = "country")
  private String countryName;

  private String city;
}
