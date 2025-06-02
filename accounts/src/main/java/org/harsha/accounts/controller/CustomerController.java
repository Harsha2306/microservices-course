package org.harsha.accounts.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.harsha.accounts.dto.AllDetailsDto;
import org.harsha.accounts.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = (MediaType.APPLICATION_JSON_VALUE))
@RequiredArgsConstructor
@Validated
public class CustomerController {

  private final ICustomerService iCustomerService;

  private Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @GetMapping("fetchCustomerDetails")
  public ResponseEntity<AllDetailsDto> allDetails(
      @RequestHeader("eazyBank-correlation-id") String correlationId,
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    logger.debug("eazyBank-correlation-id found: {} ", correlationId);
    return ResponseEntity.ok(iCustomerService.fetchCustomerDetails(mobileNumber, correlationId));
  }
}
