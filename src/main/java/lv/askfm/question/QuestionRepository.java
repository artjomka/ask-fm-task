package lv.askfm.question;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

  List<Question> findByCountryCode(String countryCode);

  Long countByCreatedBetweenAndCountryCode(LocalDateTime startDate, LocalDateTime endDate, String countryCode);
}
