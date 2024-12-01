package edu.miu.apsd.eWallet.dto.request;

import edu.miu.apsd.eWallet.model.Role;
import edu.miu.apsd.eWallet.model.UserGender;

public record RegisterRequestDTO(
        String name,
        String email,
        String password,
        Long phone,
        UserGender gender,
        Role role
) {}
