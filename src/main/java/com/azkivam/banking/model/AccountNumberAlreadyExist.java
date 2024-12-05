package com.azkivam.banking.model;

public class AccountNumberAlreadyExist extends RuntimeException{
    public AccountNumberAlreadyExist(String message) {
        super(message);
    }
}
