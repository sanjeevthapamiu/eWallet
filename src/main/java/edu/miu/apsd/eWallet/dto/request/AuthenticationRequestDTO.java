package edu.miu.apsd.eWallet.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO (
        @NotBlank(message = "Email address is required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {}
