package com.azkivam.banking.model;

import javax.money.MonetaryAmount;

public interface WithdrawTransactionBehavior {
    Account withdraw(Account account, MonetaryAmount amount);
}
