package lv.askfm.question.validation;

import lv.askfm.country.Country;
import lv.askfm.question.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionValidationService implements QuestionValidation {

  private final BadWordsCheck badWordsCheck;
  private final QuestionLimitCheck questionLimitCheck;

  public QuestionValidationService(BadWordsCheck badWordsCheck, QuestionLimitCheck questionLimitCheck) {
    this.badWordsCheck = badWordsCheck;
    this.questionLimitCheck = questionLimitCheck;
  }

  public void validate(Question question, Country country) {
    badWordsCheck.validate(question);
    questionLimitCheck.validateQuestionsLimit(country, question.getCreated());
  }
}
