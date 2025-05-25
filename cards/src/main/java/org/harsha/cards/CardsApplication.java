package org.harsha.cards;

import org.harsha.cards.dto.CardContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = CardContactInfoDto.class)
public class CardsApplication {
  public static void main(String[] args) {
    SpringApplication.run(CardsApplication.class, args);
  }
}
