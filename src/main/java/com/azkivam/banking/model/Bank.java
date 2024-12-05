package com.azkivam.banking.model;

import com.azkivam.banking.repository.Accounts;
import com.azkivam.banking.service.BankService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import javax.money.MonetaryAmount;
import java.util.Optional;

@Transactional
public abstract class Bank implements BankService {

    protected WithdrawTransactionBehavior withdrawTransactionBehavior;
    protected DepositTransactionBehavior depositTransactionBehavior;
    protected TransferTransactionBehavior transferTransactionBehavior;
    protected Accounts accountRepository;
    protected ApplicationEventPublisher events;

    public Account createAccount(String holderName, String accountNumber){
        Optional<Account> accountByAccountNumber = accountRepository.findAccountByAccountNumber(accountNumber);
        if(accountByAccountNumber.isPresent()){
            throw new AccountNumberAlreadyExist(String.format("account with account number %s already exist",accountNumber));
        }
        return accountRepository.save(new Account(holderName, accountNumber));
    }

    @Override
    public Optional<Account> retrieveAccount(Long id){
        return accountRepository.findById(id);
    }

    @Override
    public Account performWithdrawTransaction(Account account, MonetaryAmount amount){
        Optional<Account> optionalAccount = accountRepository.findById(account.getId());
        Account retrievedAccount = optionalAccount.orElseThrow(()-> new IllegalArgumentException(String.format("account %s does not exist", account.getId())));
        events.publishEvent(new TransactionComplete(new TransactionInfo(account.getAccountNumber(), TransactionType.WITHDRAW,amount)));
        return withdrawTransactionBehavior.withdraw(retrievedAccount, amount);
    }

    public record TransactionComplete(TransactionInfo transactionInfo){}

    @Override
    public Account performDepositTransaction(Account account, MonetaryAmount amount){
        Optional<Account> optionalAccount = accountRepository.findById(account.getId());
        Account retrievedAccount = optionalAccount.orElseThrow(()-> new IllegalArgumentException(String.format("account %s does not exist", account.getId())));
        events.publishEvent(new TransactionComplete(new TransactionInfo(account.getAccountNumber(), TransactionType.DEPOSIT,amount)));
        return depositTransactionBehavior.deposit(retrievedAccount, amount);
    }

    @Override
    public Account performTransferTransaction(String srcAccountNumber, String destAccountNumber, MonetaryAmount amount){
        Optional<Account> optionalSourceAccount = accountRepository.findAccountByAccountNumber(srcAccountNumber);
        Optional<Account> optionalDestinationAccount = accountRepository.findAccountByAccountNumber(destAccountNumber);
        Account retrievedSourceAccount = optionalSourceAccount.orElseThrow(()-> new IllegalArgumentException(String.format("account with account number %s does not exist", srcAccountNumber)));
        Account retrievedDestinationAccount = optionalDestinationAccount.orElseThrow(()-> new IllegalArgumentException(String.format("account with account number %s does not exist", destAccountNumber)));
        events.publishEvent(new TransactionComplete(new TransactionInfo(retrievedSourceAccount.getAccountNumber(), TransactionType.WITHDRAW,amount)));
        events.publishEvent(new TransactionComplete(new TransactionInfo(retrievedDestinationAccount.getAccountNumber(), TransactionType.DEPOSIT,amount)));
        return transferTransactionBehavior.transfer(retrievedSourceAccount, retrievedDestinationAccount, amount);
    }
}
