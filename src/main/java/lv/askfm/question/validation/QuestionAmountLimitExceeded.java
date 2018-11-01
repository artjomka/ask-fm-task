package lv.askfm.question.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuestionAmountLimitExceeded extends RuntimeException {

  QuestionAmountLimitExceeded(String message) {
    super(message);
  }
}
