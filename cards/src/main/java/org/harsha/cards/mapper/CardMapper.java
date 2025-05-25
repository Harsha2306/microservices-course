package org.harsha.cards.mapper;

import org.harsha.cards.dto.CardDto;
import org.harsha.cards.entity.Card;

public class CardMapper {
  private CardMapper() {}

  public static CardDto mapToCardDto(Card card) {
    return CardDto.builder()
        .cardNumber(card.getCardNumber())
        .cardType(card.getCardType())
        .mobileNumber(card.getMobileNumber())
        .totalLimit(card.getTotalLimit())
        .availableAmount(card.getAvailableAmount())
        .amountUsed(card.getAmountUsed())
        .build();
  }
}
