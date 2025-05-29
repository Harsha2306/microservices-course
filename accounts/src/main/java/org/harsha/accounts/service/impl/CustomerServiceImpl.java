package org.harsha.accounts.service.impl;

import lombok.RequiredArgsConstructor;
import org.harsha.accounts.constants.AccountConstants;
import org.harsha.accounts.dto.AllDetailsDto;
import org.harsha.accounts.dto.CardDto;
import org.harsha.accounts.dto.LoanDto;
import org.harsha.accounts.entity.Account;
import org.harsha.accounts.entity.Customer;
import org.harsha.accounts.exception.ResourceNotFoundException;
import org.harsha.accounts.mapper.AccountMapper;
import org.harsha.accounts.repository.AccountRepository;
import org.harsha.accounts.repository.CustomerRepository;
import org.harsha.accounts.service.ICustomerService;
import org.harsha.accounts.service.client.CardsFeignClient;
import org.harsha.accounts.service.client.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;
  private final CardsFeignClient cardsFeignClient;
  private final LoansFeignClient loansFeignClient;

  @Override
  public AllDetailsDto fetchCustomerDetails(String mobileNumber) {
    Customer customer =
        customerRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        AccountConstants.CUSTOMER, "mobileNumber", mobileNumber));
    Account account =
        accountRepository
            .findByCustomerId(customer.getCustomerId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Account", "customerId", customer.getCustomerId().toString()));

    ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
    ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);

    return AllDetailsDto.builder()
        .name(customer.getName())
        .email(customer.getEmail())
        .mobileNumber(customer.getMobileNumber())
        .accountsDto(AccountMapper.mapToAccountDto(account))
        .loansDto(loanDtoResponseEntity.getBody())
        .cardsDto(cardDtoResponseEntity.getBody())
        .build();
  }
}
