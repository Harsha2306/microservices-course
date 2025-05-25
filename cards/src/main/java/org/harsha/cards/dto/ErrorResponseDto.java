package org.harsha.cards.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorResponseDto(
    String apiPath, HttpStatus errorCode, String errorMessage, LocalDateTime errorTime) {}
