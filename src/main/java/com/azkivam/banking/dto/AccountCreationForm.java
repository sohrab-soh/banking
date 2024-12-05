package com.azkivam.banking.dto;

public final class AccountCreationForm {
    private final String accountNumber;
    private final String holderName;

    public AccountCreationForm(String accountNumber, String holderName) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }
}
