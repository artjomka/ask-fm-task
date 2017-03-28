package lv.askfm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lv.askfm.repository.QuestionRepository;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.jackson.Jackson;
import ratpack.spring.config.EnableRatpack;

@SpringBootApplication
@EnableRatpack
public class Main {

  @Bean
  public Action<Chain> questions() {
    return chain -> chain
        .get("questions", ctx -> {
              QuestionRepository questionRepository = ctx.get(QuestionRepository.class);
              ctx.render(Jackson.json(questionRepository.findAll()));
            }
        );
  }

  public static void main(String... args) {
    SpringApplication.run(Main.class, args);
  }
}
