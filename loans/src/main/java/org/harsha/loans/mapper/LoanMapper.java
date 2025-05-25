package org.harsha.loans.mapper;

import org.harsha.loans.dto.LoanDto;
import org.harsha.loans.entity.Loan;

public class LoanMapper {
  private LoanMapper() {}

  public static LoanDto mapToLoanDto(Loan loan) {
    return LoanDto.builder()
        .loanNumber(loan.getLoanNumber())
        .loanType(loan.getLoanType())
        .mobileNumber(loan.getMobileNumber())
        .totalLoan(loan.getTotalLoan())
        .amountPaid(loan.getAmountPaid())
        .outstandingAmount(loan.getOutstandingAmount())
        .build();
  }
}
