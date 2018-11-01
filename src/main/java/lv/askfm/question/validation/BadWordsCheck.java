package lv.askfm.question.validation;

import lombok.extern.slf4j.Slf4j;
import lv.askfm.ApplicationProperties;
import lv.askfm.question.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BadWordsCheck {

    private final ApplicationProperties applicationProperties;

    public BadWordsCheck(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void validate(Question question) {
        final List<String> badwords = applicationProperties.getBadwords();
        final boolean badwordFound = badwords.stream()
                .anyMatch(badword -> question.getText().contains(badword));
        if (badwordFound) {
            log.warn("Found bad word in question {}", question.getText());
            throw new BadWordFound("Bad word detected in question ");
        }
    }
}
