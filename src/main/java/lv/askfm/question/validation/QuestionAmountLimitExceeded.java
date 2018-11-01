package lv.askfm.question.validation;

public class QuestionAmountLimitExceeded extends RuntimeException {

  public QuestionAmountLimitExceeded(String message) {
    super(message);
  }
}
