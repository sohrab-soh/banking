package com.azkivam.banking.model;

import javax.money.MonetaryAmount;

public interface TransferTransactionBehavior {
    Account transfer(Account srcAccount, Account destAccount, MonetaryAmount amount);
}
