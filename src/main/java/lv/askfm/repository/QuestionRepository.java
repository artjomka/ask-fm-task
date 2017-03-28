package lv.askfm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.askfm.domain.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
