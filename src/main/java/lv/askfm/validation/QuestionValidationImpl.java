package lv.askfm.validation;

import lv.askfm.domain.Question;

public class QuestionValidationImpl implements QuestionValidation {

  private final BadWordsCheck badWordsCheck;

  public QuestionValidationImpl(BadWordsCheck badWordsCheck) {
    this.badWordsCheck = badWordsCheck;
  }


  @Override
  public void validate(Question question) {
    badWordsCheck.validate(question);
  }
}
