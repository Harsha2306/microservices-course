package org.harsha.cards.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardContactInfoDto {
  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;
}
