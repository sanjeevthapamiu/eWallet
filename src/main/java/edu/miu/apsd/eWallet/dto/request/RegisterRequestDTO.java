package edu.miu.apsd.eWallet.dto.request;

import edu.miu.apsd.eWallet.model.Role;
import edu.miu.apsd.eWallet.model.UserGender;
import edu.miu.apsd.eWallet.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email
        @UniqueEmail
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "Phone is required")
        Long phone,

        @NotNull(message = "Gender is required")
        UserGender gender,

        @NotNull(message = "Role is required")
        Role role
) {}
