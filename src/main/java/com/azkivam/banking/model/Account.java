package com.azkivam.banking.model;

import com.azkivam.banking.Currencies;
import jakarta.persistence.*;
import org.javamoney.moneta.Money;
import javax.money.MonetaryAmount;

@Entity
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(name = "account_id_seq", sequenceName = "ACCOUNT_ID_SEQ")
    private Long id;

    public Account() {
    }

    @Version
    private Long version;


    private MonetaryAmount balance;

    private String holderName;

    @Column(unique = true)
    private String accountNumber;

    public Account(String holderName, String accountNumber) {
        this.id = null;
        this.balance = Money.of(2_000_000, Currencies.IRR);
        this.holderName = holderName;
        this.accountNumber = accountNumber;
    }


    public Long getId() {
        return id;
    }

    public MonetaryAmount getBalance() {
        return this.balance;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Boolean hasSufficientFunds(MonetaryAmount amount){
        return this.balance.isGreaterThanOrEqualTo(amount);
    }

    public Account withdraw(MonetaryAmount amount) {
        if(!hasSufficientFunds(amount)){
            throw new IllegalArgumentException("insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
        return this;
    }

    public Account deposit(MonetaryAmount amount){
        this.balance = this.balance.add(amount);
        return this;
    }
}
