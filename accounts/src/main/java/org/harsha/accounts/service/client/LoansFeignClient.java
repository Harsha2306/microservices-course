package org.harsha.accounts.service.client;

import org.harsha.accounts.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {
  @GetMapping(value = "/api/fetch", consumes = "application/json")
  ResponseEntity<LoanDto> fetchLoanDetails(
      @RequestParam String mobileNumber,
      @RequestHeader("eazyBank-correlation-id") String correlationId);
}
