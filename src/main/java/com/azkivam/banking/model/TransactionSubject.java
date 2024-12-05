package com.azkivam.banking.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionSubject {

    private TransactionInfo transactionInfo;
    private final List<TransactionObserver> transactionObservers;

    public TransactionSubject() {
        this.transactionObservers = new ArrayList<>();
    }


    public void registerTransactionObserver(TransactionObserver observer) {
        transactionObservers.add(observer);
    }


    public void removeTransactionObserver(TransactionObserver observer) {
        transactionObservers.remove(observer);
    }


    private void notifyTransactionObservers() {
        for (var transactionObserver : transactionObservers) {
            transactionObserver.onTransaction(transactionInfo.accountNumber(), transactionInfo.transactionType(), transactionInfo.amount());
        }
    }

    public void setTransactionInfo(TransactionInfo transactionInfo){
        this.transactionInfo = transactionInfo;
        notifyTransactionObservers();
    }
}
