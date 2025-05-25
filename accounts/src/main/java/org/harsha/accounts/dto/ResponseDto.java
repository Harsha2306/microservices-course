package org.harsha.accounts.dto;

import lombok.Builder;

@Builder
public record ResponseDto(String statusCode, String statusMsg) {}
