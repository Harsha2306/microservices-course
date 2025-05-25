package org.harsha.accounts.mapper;

import org.harsha.accounts.dto.CustomerDto;
import org.harsha.accounts.entity.Customer;

public class CustomerMapper {
  private CustomerMapper() {}

  public static Customer mapToCustomer(CustomerDto customerDto) {
    return Customer.builder()
        .name(customerDto.name())
        .email(customerDto.email())
        .mobileNumber(customerDto.mobileNumber())
        .build();
  }
}
