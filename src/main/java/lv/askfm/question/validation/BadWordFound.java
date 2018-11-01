package lv.askfm.question.validation;

public class BadWordFound extends RuntimeException {

  public BadWordFound(String message) {
    super(message);
  }
}
