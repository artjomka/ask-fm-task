package lv.askfm.validation;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lv.askfm.config.ApplicationProperties;
import lv.askfm.domain.Question;
import lv.askfm.rest.exception.BadWordFound;

@Component
public class BadWordsCheck implements QuestionValidation {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private final ApplicationProperties applicationProperties;

  public BadWordsCheck(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Override
  public void validate(Question question) {
    final List<String> badwords = applicationProperties.getBadwords();
    final boolean badwordFound = badwords.parallelStream().anyMatch(badword -> question.getText().contains(badword));
    if (badwordFound) {
      LOG.warn("Found bad word in question {}", question.getText());
      throw new BadWordFound("Bad word detected in question ");
    }
  }
}
