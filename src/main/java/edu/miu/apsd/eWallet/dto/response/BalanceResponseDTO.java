package edu.miu.apsd.eWallet.dto.response;

public record BalanceResponseDTO (
        float total,
        float income,
        float expense
) {}
