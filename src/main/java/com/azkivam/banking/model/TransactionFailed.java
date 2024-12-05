package com.azkivam.banking.model;

public class TransactionFailed extends RuntimeException {
    public TransactionFailed(String message) {
        super(message);
    }
}
