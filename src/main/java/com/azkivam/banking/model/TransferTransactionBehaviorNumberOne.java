package com.azkivam.banking.model;

import org.springframework.stereotype.Service;
import javax.money.MonetaryAmount;

@Service
public class TransferTransactionBehaviorNumberOne implements TransferTransactionBehavior{
    @Override
    public Account transfer(Account sourceAccount, Account destinationAccount, MonetaryAmount amount) {
        if(!sourceAccount.hasSufficientFunds(amount)){
            throw new InsufficientBalanceException("insufficient balance");
        }
        Account withdrawn = sourceAccount.withdraw(amount);
        Account deposited = destinationAccount.deposit(amount);
        return withdrawn;
    }
}
