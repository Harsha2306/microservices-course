package org.harsha.accounts;

import org.harsha.accounts.dto.AccountContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = AccountContactInfoDto.class)
@EnableFeignClients
public class AccountsApplication {
  public static void main(String[] args) {
    SpringApplication.run(AccountsApplication.class, args);
  }
}
