package lv.askfm.rest;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE, params = "countryCode")
  public ResponseEntity<List<Question>> findQuestionByCountry(@PathParam("countryCode") String countryCode) {
    final List<Question> questions = questionService.findQuestionsByCountryCode(countryCode);
    return new ResponseEntity<>(questions, HttpStatus.OK);
  }

  @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Question>> findAllQuestions() {
    final List<Question> questions = questionService.findAll();
    return new ResponseEntity<>(questions, HttpStatus.OK);
  }
}
