package org.harsha.accounts.service.client;

import org.harsha.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

  @Override
  public ResponseEntity<LoanDto> fetchLoanDetails(String mobileNumber, String correlationId) {
    return null;
  }
}
