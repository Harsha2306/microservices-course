package org.harsha.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record AllDetailsDto(
    @NotEmpty(message = "Name can not be a null or empty")
        @Size(
            min = 5,
            max = 30,
            message = "The length of the customer name should be between 5 and 30")
        String name,
    @NotEmpty(message = "Email address can not be a null or empty")
        @Email(message = "Email address should be a valid value")
        String email,
    @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
        String mobileNumber,
    AccountDto accountsDto,
    LoanDto loansDto,
    CardDto cardsDto) {}
