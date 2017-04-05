package lv.askfm.rest.exception;

import org.springframework.stereotype.Component;

@Component
public class BadWordFound extends RuntimeException {

  public BadWordFound(String message) {
    super(message);
  }
}
