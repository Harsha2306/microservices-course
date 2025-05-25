package org.harsha.loans.dto;

import lombok.Builder;

@Builder
public record ResponseDto(String statusCode, String statusMsg) {}
