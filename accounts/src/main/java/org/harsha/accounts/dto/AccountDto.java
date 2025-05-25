package org.harsha.accounts.dto;

import lombok.Builder;

@Builder
public record AccountDto(Long accountNumber, String accountType, String branchAddress) {}
