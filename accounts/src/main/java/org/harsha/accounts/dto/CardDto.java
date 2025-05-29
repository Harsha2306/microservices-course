package org.harsha.accounts.dto;

import lombok.Builder;

@Builder
public record CardDto(
    String mobileNumber,
    String cardNumber,
    String cardType,
    int totalLimit,
    int amountUsed,
    int availableAmount) {}
