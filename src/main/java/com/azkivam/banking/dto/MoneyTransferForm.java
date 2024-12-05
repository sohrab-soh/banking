package com.azkivam.banking.dto;

import javax.money.MonetaryAmount;

public final class MoneyTransferForm {
    private final String sourceAccountNumber;
    private final String destinationAccountNumber;
    private final MonetaryAmount amount;

    public MoneyTransferForm(String sourceAccountNumber, String destinationAccountNumber, MonetaryAmount amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }
}
    