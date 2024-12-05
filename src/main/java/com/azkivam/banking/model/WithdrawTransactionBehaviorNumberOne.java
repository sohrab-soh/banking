package com.azkivam.banking.model;

import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;

@Service
public class WithdrawTransactionBehaviorNumberOne implements WithdrawTransactionBehavior {
    @Override
    public Account withdraw(Account account, MonetaryAmount amount) {
        if(!account.hasSufficientFunds(amount)){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        return account.withdraw(amount);
    }
}
