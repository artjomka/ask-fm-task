package lv.askfm.question.validation;

import lv.askfm.country.Country;
import lv.askfm.question.Question;

public interface QuestionValidation {

  void validate(Question question, Country country);
}
