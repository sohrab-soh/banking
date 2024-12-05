package com.azkivam.banking.model;

import com.azkivam.banking.repository.Accounts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
public class BankNumberOne extends Bank{
    public BankNumberOne(Accounts accounts,
                         ApplicationEventPublisher events,
                         @Qualifier("depositTransactionBehaviorNumberOne") DepositTransactionBehavior depositTransactionBehavior,
                         @Qualifier("transferTransactionBehaviorNumberOne") TransferTransactionBehavior transferTransactionBehavior,
                         @Qualifier("withdrawTransactionBehaviorNumberOne") WithdrawTransactionBehavior withdrawTransactionBehavior
                         ) {
        this.accountRepository = accounts;
        this.depositTransactionBehavior = depositTransactionBehavior;
        this.transferTransactionBehavior = transferTransactionBehavior;
        this.withdrawTransactionBehavior = withdrawTransactionBehavior;
        this.events = events;
    }
}
