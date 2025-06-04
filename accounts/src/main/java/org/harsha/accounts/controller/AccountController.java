package org.harsha.accounts.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.harsha.accounts.constants.AccountConstants;
import org.harsha.accounts.dto.AccountContactInfoDto;
import org.harsha.accounts.dto.CustomerDetailsDto;
import org.harsha.accounts.dto.CustomerDto;
import org.harsha.accounts.dto.ResponseDto;
import org.harsha.accounts.service.IAccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(path = "/api", produces = (MediaType.APPLICATION_JSON_VALUE))
@RequiredArgsConstructor
@Validated
@Slf4j
public class AccountController {
  @Value("${build.version}")
  private String buildVersion;

  private final IAccountService iAccountService;

  private final Environment environment;

  private final AccountContactInfoDto accountContactInfoDto;

  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    iAccountService.createAccount(customerDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ResponseDto.builder()
                .statusCode(AccountConstants.STATUS_201)
                .statusMsg(AccountConstants.MESSAGE_201)
                .build());
  }

  @GetMapping("/fetch")
  public ResponseEntity<CustomerDetailsDto> fetchAccountDetails(
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    CustomerDetailsDto customerDetailsDto = iAccountService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetails(
      @Valid @RequestBody CustomerDetailsDto customerDetailsDto) {
    boolean isUpdated = iAccountService.updateAccount(customerDetailsDto);

    return isUpdated
        ? ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200))
        : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccountDetails(
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
    return isDeleted
        ? ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200))
        : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
  }

  @GetMapping("/build-info")
  @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
  public ResponseEntity<String> getBuildInfo() throws TimeoutException {
    log.debug("getBuildInfo() method invoked");
   return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
    log.debug("getBuildInfoFallback() method invoked");
    return ResponseEntity.status(HttpStatus.OK).body("0.9");
  }

  @GetMapping("/java-version")
  @RateLimiter(name = "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
    return ResponseEntity.status(HttpStatus.OK).body("JAVA_17");
  }

  @GetMapping("/contact-info")
  public ResponseEntity<AccountContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(accountContactInfoDto);
  }
}
