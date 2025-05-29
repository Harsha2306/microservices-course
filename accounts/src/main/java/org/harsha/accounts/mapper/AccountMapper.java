package org.harsha.accounts.mapper;

import org.harsha.accounts.dto.AccountDto;
import org.harsha.accounts.dto.CustomerDetailsDto;
import org.harsha.accounts.entity.Account;

public class AccountMapper {
  private AccountMapper() {}

  public static AccountDto mapToAccountDto(CustomerDetailsDto customerDetailsDto) {
    return AccountDto.builder()
        .accountNumber(customerDetailsDto.accountNumber())
        .accountType(customerDetailsDto.accountType())
        .branchAddress(customerDetailsDto.branchAddress())
        .build();
  }

  public static AccountDto mapToAccountDto(Account account) {
    return AccountDto.builder()
        .accountNumber(account.getAccountNumber())
        .accountType(account.getAccountType())
        .branchAddress(account.getBranchAddress())
        .build();
  }
}
