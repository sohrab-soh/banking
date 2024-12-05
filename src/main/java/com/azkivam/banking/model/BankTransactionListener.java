package com.azkivam.banking.model;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class BankTransactionListener {
    private TransactionSubject transactionSubject;

    public BankTransactionListener(TransactionSubject transactionSubject) {
        this.transactionSubject = transactionSubject;
    }

    @Async
    @TransactionalEventListener
    void onEvent(Bank.TransactionComplete transactionComplete){
        transactionSubject.setTransactionInfo(transactionComplete.transactionInfo());
    }
}
