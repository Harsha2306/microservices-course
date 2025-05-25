package org.harsha.cards.dto;

import lombok.Builder;

@Builder
public record ResponseDto(String statusCode, String statusMsg) {}
