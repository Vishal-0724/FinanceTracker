package com.finance.financetracker.exception;

public class TransactionNotExistsException extends RuntimeException {
    public TransactionNotExistsException(String message) {
        super(message);
    }
}
