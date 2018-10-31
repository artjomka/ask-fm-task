package lv.askfm.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CountryResponse {

  private String countryCode;

  @JsonProperty(value = "country")
  private String countryName;

  private String city;
}
