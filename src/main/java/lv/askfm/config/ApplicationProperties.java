package lv.askfm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ApplicationProperties {

  @Value("${country.resolver.url}")
  private String url;

  @Value("${country.defaultlimit}")
  private Integer defaultlimit;

}
