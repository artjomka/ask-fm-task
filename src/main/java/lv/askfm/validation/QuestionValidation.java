package lv.askfm.validation;

import lv.askfm.domain.Country;
import lv.askfm.domain.Question;

public interface QuestionValidation {

  void validate(Question question, Country country);
}
