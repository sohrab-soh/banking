package com.azkivam.banking.model;

import javax.money.MonetaryAmount;

public interface DepositTransactionBehavior {
    Account deposit(Account account, MonetaryAmount amount);
}
