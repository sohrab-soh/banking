package com.azkivam.banking.model;

import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class TransactionLogger implements TransactionObserver{
    private TransactionSubject transactionSubject;


    public TransactionLogger(TransactionSubject transactionSubject) {
        this.transactionSubject = transactionSubject;
        transactionSubject.registerTransactionObserver(this);
    }

    private static final String FILE_PATH = "transactionLogs.txt";
    @Override
    public void onTransaction(String accountNumber, TransactionType transactionType, MonetaryAmount amount) {
        try {
            Files.writeString(Paths.get(FILE_PATH),
                    String.format("%s   %s  %s" + System.lineSeparator(),accountNumber,transactionType, amount.getCurrency().toString() + amount.getNumber().toString()),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: "+ e.getMessage());
        }
    }
}
