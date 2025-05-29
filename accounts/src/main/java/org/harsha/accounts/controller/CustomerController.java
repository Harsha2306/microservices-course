package org.harsha.accounts.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.harsha.accounts.dto.AllDetailsDto;
import org.harsha.accounts.service.ICustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = (MediaType.APPLICATION_JSON_VALUE))
@RequiredArgsConstructor
@Validated
public class CustomerController {

  private final ICustomerService iCustomerService;

  @GetMapping("fetchCustomerDetails")
  public ResponseEntity<AllDetailsDto> allDetails(
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    return ResponseEntity.ok(iCustomerService.fetchCustomerDetails(mobileNumber));
  }
}
