package edu.miu.apsd.eWallet.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionProductResponseDTO(
        UUID id,
        String name,
        int boughtQuantity,
        double boughtAmount
) {}
