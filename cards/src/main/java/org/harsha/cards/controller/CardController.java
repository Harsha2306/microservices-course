package org.harsha.cards.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.harsha.cards.constants.CardConstants;
import org.harsha.cards.dto.CardContactInfoDto;
import org.harsha.cards.dto.CardDto;
import org.harsha.cards.dto.ResponseDto;
import org.harsha.cards.service.ICardService;
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
public class CardController {

  @Value("${build.version}")
  private String buildVersion;

  private final Environment environment;

  private final CardContactInfoDto cardContactInfoDto;

  private final ICardService iCardService;

  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createCard(
      @Valid
          @RequestParam
          @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    iCardService.createCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(CardConstants.STATUS_201, CardConstants.MESSAGE_201));
  }

  @GetMapping("/fetch")
  public ResponseEntity<CardDto> fetchCardDetails(
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    CardDto cardsDto = iCardService.fetchCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardDto cardsDto) {
    boolean isUpdated = iCardService.updateCard(cardsDto);
    return isUpdated
        ? ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200))
        : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_UPDATE));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteCardDetails(
      @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    boolean isDeleted = iCardService.deleteCard(mobileNumber);
    return isDeleted
        ? ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200))
        : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_DELETE));
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
  public ResponseEntity<CardContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(cardContactInfoDto);
  }
}
