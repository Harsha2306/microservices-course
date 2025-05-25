package org.harsha.accounts.service;

import org.harsha.accounts.dto.CustomerDetailsDto;
import org.harsha.accounts.dto.CustomerDto;

public interface IAccountService {
  void createAccount(CustomerDto customerDto);

  CustomerDetailsDto fetchAccount(String mobileNumber);

  boolean updateAccount(CustomerDetailsDto customerDetailsDto);

  boolean deleteAccount(String mobileNumber);
}
