package edu.miu.apsd.eWallet.dto.request;

public record DepositRequestDTO(
        Double amount,
        String bank
) {}
