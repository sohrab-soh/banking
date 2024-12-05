package com.azkivam.banking.model;

import org.springframework.stereotype.Service;
import javax.money.MonetaryAmount;

@Service
public class DepositTransactionBehaviorNumberOne implements DepositTransactionBehavior {

    @Override
    public Account deposit(Account account, MonetaryAmount amount) {
        return account.deposit(amount);
    }
}
