package org.harsha.accounts.mapper;

import org.harsha.accounts.dto.CustomerDetailsDto;
import org.harsha.accounts.entity.Account;
import org.harsha.accounts.entity.Customer;

public class CustomerDetailsMapper {
  private CustomerDetailsMapper() {}

  public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, Account account) {
    return CustomerDetailsDto.builder()
        .name(customer.getName())
        .email(customer.getEmail())
        .mobileNumber(customer.getMobileNumber())
        .accountNumber(account.getAccountNumber())
        .accountType(account.getAccountType())
        .branchAddress(account.getBranchAddress())
        .build();
  }
}
