package lv.askfm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

  @ManyToOne
  @Cascade(CascadeType.MERGE)
  private Country country;
}
