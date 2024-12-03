package edu.miu.apsd.eWallet.dto.request;

public record SendRequestDTO (
        double amount,
        String receiverEmail
) {}
