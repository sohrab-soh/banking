package com.azkivam.banking.repository;

import com.azkivam.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Accounts extends JpaRepository<Account,Long> {

    Optional<Account> findAccountByAccountNumber(String accountNumber);

}
