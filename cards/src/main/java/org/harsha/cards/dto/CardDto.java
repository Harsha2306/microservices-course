package org.harsha.cards.dto;

import lombok.Builder;

@Builder
public record CardDto(
    String mobileNumber,
    String cardNumber,
    String cardType,
    int totalLimit,
    int amountUsed,
    int availableAmount) {}
