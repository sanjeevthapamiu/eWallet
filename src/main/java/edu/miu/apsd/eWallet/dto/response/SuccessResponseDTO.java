package edu.miu.apsd.eWallet.dto.response;

import org.springframework.http.HttpStatus;

public record SuccessResponseDTO (
    boolean success,
    int statusCode,
    Object message
) {

    SuccessResponseDTO(int statusCode, Object message) {
        this(true, statusCode, message);
    }

    public static SuccessResponseDTO ok(Object message) {
        return new SuccessResponseDTO(HttpStatus.OK.value(), message);
    }

}
