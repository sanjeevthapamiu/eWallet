package edu.miu.apsd.eWallet.dto.request;

public record ProductRequestDTO (
        String name,
        String description,
        Double price,
        Integer stock
) {}
