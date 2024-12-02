package edu.miu.apsd.eWallet.dto.response;

import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        double price,
        int stock,
        MerchantResponseDTO merchant
) {}