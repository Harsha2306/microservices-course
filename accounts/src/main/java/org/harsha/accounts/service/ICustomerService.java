package org.harsha.accounts.service;

import org.harsha.accounts.dto.AllDetailsDto;

public interface ICustomerService {
  AllDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
