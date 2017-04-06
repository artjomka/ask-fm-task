package lv.askfm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString(exclude = {"id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column
  private LocalDateTime created = LocalDateTime.now();

  @Column
  private String text;

  @Column
  private String countryCode;
}
