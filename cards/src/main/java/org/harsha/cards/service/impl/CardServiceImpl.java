package org.harsha.cards.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.harsha.cards.constants.CardConstants;
import org.harsha.cards.dto.CardDto;
import org.harsha.cards.entity.Card;
import org.harsha.cards.exception.CardAlreadyExistsException;
import org.harsha.cards.exception.ResourceNotFoundException;
import org.harsha.cards.mapper.CardMapper;
import org.harsha.cards.repository.CardRepository;
import org.harsha.cards.service.ICardService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements ICardService {
  private final CardRepository cardRepository;

  private Random random = new Random();

  @Override
  public void createCard(String mobileNumber) {
    Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
    if (optionalCard.isPresent())
      throw new CardAlreadyExistsException(
          "Card already registered with given mobileNumber " + mobileNumber);
    Card savedCard = cardRepository.save(createNewCard(mobileNumber));
    log.info("card with id {} saved", savedCard.getCardId());
  }

  private Card createNewCard(String mobileNumber) {
    long randomCardNumber = 100000000000L + random.nextInt(900000000);
    return Card.builder()
        .cardNumber(Long.toString(randomCardNumber))
        .mobileNumber(mobileNumber)
        .cardType(CardConstants.CREDIT_CARD)
        .totalLimit(CardConstants.NEW_CARD_LIMIT)
        .amountUsed(0)
        .availableAmount(CardConstants.NEW_CARD_LIMIT)
        .build();
  }

  @Override
  public CardDto fetchCard(String mobileNumber) {
    Card card =
        cardRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    return CardMapper.mapToCardDto(card);
  }

  @Override
  public boolean updateCard(CardDto cardDto) {
    Card card =
        cardRepository
            .findByCardNumber(cardDto.cardNumber())
            .orElseThrow(
                () -> new ResourceNotFoundException("Card", "cardNumber", cardDto.cardNumber()));

    card.setCardNumber(cardDto.cardNumber());
    card.setCardType(cardDto.cardType());
    card.setMobileNumber(cardDto.mobileNumber());
    card.setTotalLimit(cardDto.totalLimit());
    card.setAmountUsed(cardDto.amountUsed());
    card.setAvailableAmount(cardDto.availableAmount());

    Card updatedCard = cardRepository.save(card);
    log.info("card with id {} updated", updatedCard.getCardId());
    return true;
  }

  @Override
  public boolean deleteCard(String mobileNumber) {
    Card card =
        cardRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    cardRepository.deleteById(card.getCardId());
    return true;
  }
}
