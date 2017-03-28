package lv.askfm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
public class Country {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String code;

  @Column
  private Long limitPerSecond;

}
