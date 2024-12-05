package com.azkivam.banking.controller;

import com.azkivam.banking.dto.AccountCreationForm;
import com.azkivam.banking.dto.MoneyTransferForm;
import com.azkivam.banking.model.Account;
import com.azkivam.banking.model.AccountNumberAlreadyExist;
import com.azkivam.banking.model.InsufficientBalanceException;
import com.azkivam.banking.service.BankService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.money.MonetaryAmount;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class bankController {

    private BankService bank;

    public bankController(BankService bank){
        this.bank = bank;
    }

    @PostMapping()
    public ResponseEntity<?> createAccount(@RequestBody AccountCreationForm form){
        Account account = bank.createAccount(form.getHolderName(), form.getAccountNumber());
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> retrieveAccount(@PathVariable(name = "accountId") Long id){
        Optional<Account> account = bank.retrieveAccount(id);
        if(account.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(account.get());
    }

    @PatchMapping("/{accountId}/withdraw")
    public Account withdraw(@PathVariable("accountId") Account account, @RequestParam(name="amount")MonetaryAmount amount){
        return bank.performWithdrawTransaction(account,amount);
    }

    @PatchMapping("/{accountId}/deposit")
    public Account deposit(@PathVariable("accountId") Account account, @RequestParam(name="amount")MonetaryAmount amount){
        return bank.performDepositTransaction(account, amount);
    }

    @PatchMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody MoneyTransferForm moneyTransferForm){
        bank.performTransferTransaction(moneyTransferForm.getSourceAccountNumber(),
                moneyTransferForm.getDestinationAccountNumber(),
                moneyTransferForm.getAmount());
        return ResponseEntity.ok("Successful money transfer");
    }


    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<?> handle(OptimisticLockingFailureException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("pleas try again later...");
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<?> handle(InsufficientBalanceException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(AccountNumberAlreadyExist.class)
    public ResponseEntity<?> handle(AccountNumberAlreadyExist e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
