package edu.miu.apsd.eWallet.dto.request;

import java.util.UUID;

public record PurchaseRequestDTO(
//        Float amount,
        UUID productId,
        Integer stock
) {}
