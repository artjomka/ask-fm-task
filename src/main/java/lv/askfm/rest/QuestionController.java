package lv.askfm.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lv.askfm.domain.Question;
import lv.askfm.service.QuestionService;

@RestController
public class QuestionController {

  private final QuestionService questionService;

  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping(path = "/ask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Question> ask(@RequestBody Question question, HttpServletRequest request) {
    final Question askedQuestion = questionService.ask(question, request.getRemoteAddr());

    return new ResponseEntity<>(askedQuestion, HttpStatus.OK);
  }
}
