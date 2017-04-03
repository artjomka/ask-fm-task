package lv.askfm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"id", "limitPerSecond"})
@ToString
public class Country {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String code;

  @Column
  private Integer limitPerSecond;

}
