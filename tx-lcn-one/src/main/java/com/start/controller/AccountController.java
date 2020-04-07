package com.start.controller;

import com.start.entity.root.Account;
import com.start.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @RequestMapping(value = "/get/by-id")
    public Account getAccountById(@RequestParam("id") int id){
        return accountService.getAccountById(id);
    }

}
