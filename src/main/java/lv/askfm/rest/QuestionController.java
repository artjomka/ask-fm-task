package lv.askfm.rest;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private final QuestionService questionService;

  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping(path = "/ask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Question> ask(@RequestBody Question question, HttpServletRequest request) {
    LOG.info("Address " + request.getRemoteAddr() + " asking question");
    final Question askedQuestion = questionService.ask(question, request.getRemoteAddr(), LocalDateTime.now());
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
