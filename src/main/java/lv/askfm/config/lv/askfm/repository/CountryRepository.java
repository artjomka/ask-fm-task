package lv.askfm.config.lv.askfm.repository;

import org.springframework.data.repository.CrudRepository;

import lv.askfm.config.lv.askfm.domain.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {

}
