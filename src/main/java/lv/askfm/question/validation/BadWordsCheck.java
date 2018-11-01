package lv.askfm.question.validation;

import lv.askfm.ApplicationProperties;
import lv.askfm.question.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class BadWordsCheck {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ApplicationProperties applicationProperties;

    public BadWordsCheck(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void validate(Question question) {
        final List<String> badwords = applicationProperties.getBadwords();
        final boolean badwordFound = badwords.stream()
                .anyMatch(badword -> question.getText().contains(badword));
        if (badwordFound) {
            LOG.warn("Found bad word in question {}", question.getText());
            throw new BadWordFound("Bad word detected in question ");
        }
    }
}
