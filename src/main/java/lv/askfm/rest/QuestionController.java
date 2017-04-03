package lv.askfm.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.askfm.domain.Question;

@RestController

public class QuestionController {

  @PostMapping(path = "/ask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Question> ask() {
    return new ResponseEntity<>(new Question(), HttpStatus.OK);
  }
}
