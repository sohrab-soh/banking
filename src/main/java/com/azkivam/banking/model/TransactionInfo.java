package com.azkivam.banking.model;

import javax.money.MonetaryAmount;

public record TransactionInfo(String accountNumber, TransactionType transactionType, MonetaryAmount amount) {
}
