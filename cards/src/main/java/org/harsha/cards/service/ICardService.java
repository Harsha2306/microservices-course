package org.harsha.cards.service;

import org.harsha.cards.dto.CardDto;

public interface ICardService {
  void createCard(String mobileNumber);

  CardDto fetchCard(String mobileNumber);

  boolean updateCard(CardDto cardsDto);

  boolean deleteCard(String mobileNumber);
}
