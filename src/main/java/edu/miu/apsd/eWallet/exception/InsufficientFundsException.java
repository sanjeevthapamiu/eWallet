package edu.miu.apsd.eWallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InsufficientFundsException extends ResponseStatusException {

    public InsufficientFundsException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public InsufficientFundsException() {
        super(HttpStatus.BAD_REQUEST, "Insufficient balance to complete the transaction.");
    }

}
