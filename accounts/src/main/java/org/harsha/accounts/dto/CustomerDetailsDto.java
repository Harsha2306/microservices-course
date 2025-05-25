package org.harsha.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record CustomerDetailsDto(
    @NotEmpty(message = "Name cannot be empty")
        @Size(
            min = 5,
            max = 30,
            message = "The length of the customer name should be between 5 and 30")
        String name,
    @NotEmpty(message = "Name cannot be empty") @Email(message = "Email address should be valid")
        String email,
    @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
        String mobileNumber,
    @Digits(integer = 10, fraction = 0, message = "Account number must be 10 digits")
        @Min(value = 1000000000L, message = "Account number must be 10 digits")
        @Max(value = 9999999999L, message = "Account number must be 10 digits")
        Long accountNumber,
    @NotEmpty(message = "Account type cannot be empty") String accountType,
    @NotEmpty(message = "branch address cannot be empty") String branchAddress) {}
