package lv.askfm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

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

  @CreatedDate
  @Column(columnDefinition = "timestamp default current_timestamp", nullable = false)
  private LocalDateTime created;

  private String text;

  private String countryCode;
}
