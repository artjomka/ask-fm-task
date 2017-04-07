package lv.askfm.validation;

import org.springframework.stereotype.Component;

import lv.askfm.domain.Country;
import lv.askfm.domain.Question;

@Component
public class QuestionValidationImpl implements QuestionValidation {

  private final BadWordsCheck badWordsCheck;
  private final QuestionLimitCheck questionLimitCheck;

  public QuestionValidationImpl(BadWordsCheck badWordsCheck, QuestionLimitCheck questionLimitCheck) {
    this.badWordsCheck = badWordsCheck;
    this.questionLimitCheck = questionLimitCheck;
  }

  @Override
  public void validate(Question question, Country country) {
    badWordsCheck.validate(question);
    questionLimitCheck.validateQuestionsLimit(country, question.getCreated());
  }
}
