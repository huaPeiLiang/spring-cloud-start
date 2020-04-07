package com.start.service;

import com.start.entity.root.Account;
import com.start.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(int id){
        Account account = accountRepository.findById(id).orElseGet(Account::new);
        return account;
    }

}
