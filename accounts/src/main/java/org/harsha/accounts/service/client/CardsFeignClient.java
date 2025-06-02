package org.harsha.accounts.service.client;

import org.harsha.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {
  @GetMapping(value = "/api/fetch", consumes = "application/json")
  ResponseEntity<CardDto> fetchCardDetails(@RequestParam String mobileNumber, @RequestHeader("eazyBank-correlation-id") String correlationId);
}
