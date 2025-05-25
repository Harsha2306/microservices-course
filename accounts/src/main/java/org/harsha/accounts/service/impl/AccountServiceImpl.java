package org.harsha.accounts.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.harsha.accounts.constants.AccountConstants;
import org.harsha.accounts.dto.AccountDto;
import org.harsha.accounts.dto.CustomerDetailsDto;
import org.harsha.accounts.dto.CustomerDto;
import org.harsha.accounts.entity.Account;
import org.harsha.accounts.entity.Customer;
import org.harsha.accounts.exception.CustomerAlreadyExistsException;
import org.harsha.accounts.exception.ResourceNotFoundException;
import org.harsha.accounts.mapper.AccountMapper;
import org.harsha.accounts.mapper.CustomerDetailsMapper;
import org.harsha.accounts.mapper.CustomerMapper;
import org.harsha.accounts.repository.AccountRepository;
import org.harsha.accounts.repository.CustomerRepository;
import org.harsha.accounts.service.IAccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {

  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;
  private Random random = new Random();

  @Override
  public void createAccount(CustomerDto customerDto) {
    Optional<Customer> optionalCustomer =
        customerRepository.findByMobileNumber(customerDto.mobileNumber());
    if (optionalCustomer.isPresent())
      throw new CustomerAlreadyExistsException(
          "Customer already registered with given mobile number " + customerDto.mobileNumber());

    Customer customer = CustomerMapper.mapToCustomer(customerDto);
    Customer savedCustomer = customerRepository.save(customer);
    log.info("customer with id {} saved", savedCustomer.getCustomerId());

    Account savedAccount = accountRepository.save(createAccount(savedCustomer));
    log.info("account with id {} saved", savedAccount.getAccountNumber());
  }

  private Account createAccount(Customer customer) {
    long randomAccountNumber = 1000000000L + random.nextInt(900000000);
    return Account.builder()
        .customerId(customer.getCustomerId())
        .accountNumber(randomAccountNumber)
        .accountType(AccountConstants.SAVINGS)
        .branchAddress(AccountConstants.ADDRESS)
        .build();
  }

  @Override
  public CustomerDetailsDto fetchAccount(String mobileNumber) {
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
    return CustomerDetailsMapper.mapToCustomerDetailsDto(customer, account);
  }

  @Override
  public boolean updateAccount(CustomerDetailsDto customerDetailsDto) {
    AccountDto accountDto = AccountMapper.mapToAccountDto(customerDetailsDto);
    if (accountDto.accountNumber() == null) return false;

    Account account =
        accountRepository
            .findById(accountDto.accountNumber())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Account", "AccountNumber", customerDetailsDto.accountNumber().toString()));
    account.setAccountType(accountDto.accountType());
    account.setBranchAddress(accountDto.branchAddress());

    Account updatedAccount = accountRepository.save(account);

    Long customerId = updatedAccount.getCustomerId();
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        AccountConstants.CUSTOMER, "CustomerID", customerId.toString()));
    customer.setName(customerDetailsDto.name());
    customer.setEmail(customerDetailsDto.email());
    customer.setMobileNumber(customerDetailsDto.mobileNumber());

    Customer updatedCustomer = customerRepository.save(customer);
    log.info("updated account: {}, updated customer: {}", updatedAccount, updatedCustomer);
    return true;
  }

  @Override
  public boolean deleteAccount(String mobileNumber) {
    Customer customer =
        customerRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        AccountConstants.CUSTOMER, "mobileNumber", mobileNumber));

    accountRepository.deleteByCustomerId(customer.getCustomerId());
    customerRepository.deleteById(customer.getCustomerId());

    log.info("customer and account deleted for the customerId {}", customer.getCustomerId());
    return true;
  }
}
