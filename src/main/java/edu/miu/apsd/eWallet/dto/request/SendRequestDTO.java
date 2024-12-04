package edu.miu.apsd.eWallet.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SendRequestDTO (
        @NotNull(message = "Amount is required")
        @Positive
        Double amount,

        @NotNull(message = "receiverEmail is required")
        @Email
        String receiverEmail
) {}
