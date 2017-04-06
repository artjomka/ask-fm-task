package lv.askfm.rest.exception;

public class BadWordFound extends RuntimeException {

  public BadWordFound(String message) {
    super(message);
  }
}
