package org.harsha.cards.exception;

import org.harsha.cards.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Map<String, String> validationErrors = new HashMap<>();
    List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

    validationErrorList.forEach(
        error -> {
          String fieldName = ((FieldError) error).getField();
          String validationMsg = error.getDefaultMessage();
          validationErrors.put(fieldName, validationMsg);
        });
    return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CardAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleCardAlreadyExistsException(
      CardAlreadyExistsException exception, WebRequest webRequest) {
    return new ResponseEntity<>(
        ErrorResponseDto.builder()
            .apiPath(webRequest.getDescription(false))
            .errorCode(HttpStatus.BAD_REQUEST)
            .errorMessage(exception.getMessage())
            .errorTime(LocalDateTime.now())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
      ResourceNotFoundException exception, WebRequest webRequest) {
    return new ResponseEntity<>(
        ErrorResponseDto.builder()
            .apiPath(webRequest.getDescription(false))
            .errorCode(HttpStatus.NOT_FOUND)
            .errorMessage(exception.getMessage())
            .errorTime(LocalDateTime.now())
            .build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGlobalException(
      Exception exception, WebRequest webRequest) {
    ErrorResponseDto errorResponseDTO =
        new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception.getMessage(),
            LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
  }
}
