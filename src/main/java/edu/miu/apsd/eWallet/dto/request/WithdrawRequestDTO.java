package edu.miu.apsd.eWallet.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WithdrawRequestDTO (
        @NotNull(message = "Amount is required")
        @Positive
        Double amount,

        @NotNull(message = "Bank is required")
        String bank
) {}
