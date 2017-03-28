package lv.askfm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.askfm.domain.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

}
