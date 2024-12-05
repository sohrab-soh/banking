package com.azkivam.banking.service;

import com.azkivam.banking.model.Account;
import com.azkivam.banking.model.InsufficientBalanceException;

import javax.money.MonetaryAmount;
import java.util.Optional;

public interface BankService {
    Account createAccount(String holderName, String accountNumber);

    Optional<Account> retrieveAccount(Long id);

    Account performWithdrawTransaction(Account account, MonetaryAmount amount) throws InsufficientBalanceException;

    Account performDepositTransaction(Account account, MonetaryAmount amount);

    Account performTransferTransaction(String srcAccountNumber, String destAccountNumber, MonetaryAmount amount);
}
