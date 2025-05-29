package org.harsha.loans.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.harsha.loans.constants.LoanConstants;
import org.harsha.loans.dto.LoanDto;
import org.harsha.loans.entity.Loan;
import org.harsha.loans.exception.LoanAlreadyExistsException;
import org.harsha.loans.exception.ResourceNotFoundException;
import org.harsha.loans.mapper.LoanMapper;
import org.harsha.loans.repository.LoanRepository;
import org.harsha.loans.service.ILoanService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements ILoanService {
  private final LoanRepository loanRepository;
  private Random random = new Random();

  @Override
  public void createLoan(String mobileNumber) {
    Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
    if (optionalLoan.isPresent())
      throw new LoanAlreadyExistsException(
          "Loan already registered with given mobileNumber " + mobileNumber);
    Loan savedLoan = loanRepository.save(createNewLoan(mobileNumber));
    log.info("Loan id {} saved for Mobile Number {}", savedLoan.getLoanNumber(), mobileNumber);
  }

  private Loan createNewLoan(String mobileNumber) {
    long randomLoanNumber = 100000000000L + random.nextInt(900000000);

    return Loan.builder()
        .loanNumber(Long.toString(randomLoanNumber))
        .mobileNumber(mobileNumber)
        .loanType(LoanConstants.HOME_LOAN)
        .totalLoan(LoanConstants.NEW_LOAN_LIMIT)
        .amountPaid(0)
        .outstandingAmount(LoanConstants.NEW_LOAN_LIMIT)
        .build();
  }

  @Override
  public LoanDto fetchLoan(String mobileNumber) {
    Loan loan =
        loanRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    return LoanMapper.mapToLoanDto(loan);
  }

  @Override
  public boolean updateLoan(LoanDto loanDto) {
    Loan loan =
        loanRepository
            .findByLoanNumber(loanDto.loanNumber())
            .orElseThrow(
                () -> new ResourceNotFoundException("Loan", "loanNumber", loanDto.loanNumber()));

    loan.setLoanNumber(loanDto.loanNumber());
    loan.setLoanType(loanDto.loanType());
    loan.setMobileNumber(loanDto.mobileNumber());
    loan.setTotalLoan(loanDto.totalLoan());
    loan.setAmountPaid(loanDto.amountPaid());
    loan.setOutstandingAmount(loanDto.outstandingAmount());

    Loan updatedLoan = loanRepository.save(loan);
    log.info("loan with id {} updated", updatedLoan.getLoanNumber());
    return true;
  }

  @Override
  public boolean deleteLoan(String mobileNumber) {
    Loan loan =
        loanRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    loanRepository.deleteById(loan.getLoanId());
    return true;
  }
}
