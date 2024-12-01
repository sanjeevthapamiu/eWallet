package edu.miu.apsd.eWallet.dto.request;

public record AuthenticationRequestDTO (
        String email,
        String password
) {}
