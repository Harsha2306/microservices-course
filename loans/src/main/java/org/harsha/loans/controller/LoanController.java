package org.harsha.loans.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.harsha.loans.constants.LoanConstants;
import org.harsha.loans.dto.LoanContactInfoDto;
import org.harsha.loans.dto.LoanDto;
import org.harsha.loans.dto.ResponseDto;
import org.harsha.loans.service.ILoanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    path = "/api",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class LoanController {

  private final ILoanService iLoanService;

  private final Environment environment;

  private final LoanContactInfoDto loanContactInfoDto;

  @Value("${build.version}")
  private String buildVersion;

  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createLoan(
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    iLoanService.createLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));
  }

  @GetMapping("/fetch")
  public ResponseEntity<LoanDto> fetchLoanDetails(
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    return ResponseEntity.ok(iLoanService.fetchLoan(mobileNumber));
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto loanDto) {
    return iLoanService.updateLoan(loanDto)
        ? ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200))
        : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteLoanDetails(
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    return iLoanService.deleteLoan(mobileNumber)
        ? ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200))
        : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_DELETE));
  }

  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @GetMapping("/contact-info")
  public ResponseEntity<LoanContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(loanContactInfoDto);
  }
}
