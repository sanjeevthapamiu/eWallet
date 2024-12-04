package edu.miu.apsd.eWallet.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record PurchaseRequestDTO(
        @NotNull(message = "productID is required")
        UUID productId,

        @NotNull(message = "Stock is required")
        @Positive
        Integer stock
) {}
