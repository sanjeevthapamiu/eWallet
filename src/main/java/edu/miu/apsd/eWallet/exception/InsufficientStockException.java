package edu.miu.apsd.eWallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InsufficientStockException extends ResponseStatusException {

    public InsufficientStockException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

}
