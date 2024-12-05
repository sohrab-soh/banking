package com.azkivam.banking.model;

import javax.money.MonetaryAmount;

public interface TransactionObserver {
    void onTransaction(String accountNumber, TransactionType transactionType, MonetaryAmount amount);
}
