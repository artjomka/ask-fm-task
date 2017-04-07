package lv.askfm.rest.exception;

public class QuestionAmountLimitExceeded extends RuntimeException {

  public QuestionAmountLimitExceeded(String message) {
    super(message);
  }
}
