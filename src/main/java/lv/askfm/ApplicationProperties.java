package lv.askfm;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class ApplicationProperties {

  @Value("${country.resolver.url}")
  private String url;

  @Value("${country.defaultlimit}")
  private Integer defaultlimit;

  @Value("#{'${badwords}'.split(',')}")
  private List<String> badwords;

}
