package org.harsha.accounts.mapper;

import org.harsha.accounts.dto.AccountDto;
import org.harsha.accounts.dto.CustomerDetailsDto;

public class AccountMapper {
  private AccountMapper() {}

  public static AccountDto mapToAccountDto(CustomerDetailsDto customerDetailsDto) {
    return AccountDto.builder()
        .accountNumber(customerDetailsDto.accountNumber())
        .accountType(customerDetailsDto.accountType())
        .branchAddress(customerDetailsDto.branchAddress())
        .build();
  }
}
