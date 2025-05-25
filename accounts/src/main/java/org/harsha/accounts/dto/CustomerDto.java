package org.harsha.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CustomerDto(
    @NotEmpty(message = "Name cannot be empty")
        @Size(
            min = 5,
            max = 30,
            message = "The length of the customer name should be between 5 and 30")
        String name,
    @NotEmpty(message = "Name cannot be empty") @Email(message = "Email address should be valid")
        String email,
    @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
        String mobileNumber) {}
