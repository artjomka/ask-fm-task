package lv.askfm.validation;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lv.askfm.domain.Country;
import lv.askfm.repository.QuestionRepository;
import lv.askfm.rest.exception.QuestionAmountLimitExceeded;

@Component
public class QuestionLimitCheck {

  private final QuestionRepository questionRepository;

  public QuestionLimitCheck(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public void validateQuestionsLimit(Country country, LocalDateTime questionCreateDate) {
    final LocalDateTime afterOneSecond = questionCreateDate.plusSeconds(1);
    final Long amountOfQuestionsCreated = questionRepository.countByCreatedBetweenAndCountryCode(questionCreateDate, afterOneSecond, country.getCode());
    if (amountOfQuestionsCreated > country.getLimitPerSecond()) {
      throw new QuestionAmountLimitExceeded("Questions amount " + country.getLimitPerSecond() +
          " per second exceeded for country " + country.getName() + "; country code " + country.getCode());
    }
  }
}
