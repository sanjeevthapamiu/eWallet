package edu.miu.apsd.eWallet.dto.response;

import edu.miu.apsd.eWallet.model.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID id,
        Double amount,
        TransactionType type,
        LocalDateTime date,
        String from,
        String to,
        ProductResponseDTO product
) {}
