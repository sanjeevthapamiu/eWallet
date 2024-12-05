package edu.miu.apsd.eWallet.dto.request;

public record AddressRequestDTO (
        String street,

        String city,

        String state,

        String zip,

        String country
) {}
