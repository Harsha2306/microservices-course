package org.harsha.loans.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Loan extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long loanId;

  private String mobileNumber;
  private String loanNumber;
  private String loanType;
  private int totalLoan;
  private int amountPaid;
  private int outstandingAmount;
}
