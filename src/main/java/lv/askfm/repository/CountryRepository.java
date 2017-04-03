package lv.askfm.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.askfm.domain.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

  Optional<Country> findByCode(String code);

}
