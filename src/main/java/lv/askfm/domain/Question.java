package lv.askfm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Question {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(columnDefinition = "timestamp default current_timestamp", nullable = false)
  @NotNull
  private LocalDateTime created = LocalDateTime.now();

  @Column
  private String question;

  @ManyToOne
  private Country country;

}
