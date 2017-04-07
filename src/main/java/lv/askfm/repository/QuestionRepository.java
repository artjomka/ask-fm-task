package lv.askfm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.askfm.domain.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

  List<Question> findByCountryCode(String countryCode);

  Long countByCreatedBetweenAndCountryCode(LocalDateTime startDate, LocalDateTime endDate, String countryCode);
}
