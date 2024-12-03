package edu.miu.apsd.eWallet.dto.request;

public record WithdrawRequestDTO (
        Double amount,
        String bank
) {}
