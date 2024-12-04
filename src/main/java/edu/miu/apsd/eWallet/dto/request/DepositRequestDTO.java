package edu.miu.apsd.eWallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepositRequestDTO(
        @NotNull(message = "Amount is required")
        Double amount,

        @NotBlank(message = "Bank is required")
        String bank
) {}
