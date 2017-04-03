package lv.askfm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString(exclude = {"id"})
public class Question {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(columnDefinition = "timestamp default current_timestamp", nullable = false)
  private LocalDateTime created = LocalDateTime.now();

  @Column
  private String text;

  @ManyToOne
  private Country country;
}
