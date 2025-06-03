package org.harsha.accounts.service.client;

import org.harsha.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallBack implements CardsFeignClient {

  @Override
  public ResponseEntity<CardDto> fetchCardDetails(String mobileNumber, String correlationId) {
    return null;
  }
}
