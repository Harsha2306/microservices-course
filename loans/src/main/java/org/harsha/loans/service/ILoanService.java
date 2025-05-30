package org.harsha.loans.service;

import org.harsha.loans.dto.LoanDto;

public interface ILoanService {
  void createLoan(String mobileNumber);

  LoanDto fetchLoan(String mobileNumber);

  boolean updateLoan(LoanDto loanDto);

  boolean deleteLoan(String mobileNumber);
}
