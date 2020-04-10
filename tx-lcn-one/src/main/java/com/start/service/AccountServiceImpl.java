package com.start.service;

import com.start.entity.request.AccountTransferRequest;
import com.start.entity.root.Account;
import com.start.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    public void transfer(AccountTransferRequest requestVo){
        Optional<Account> sourceAccountOp = accountRepository.findFirstByIdAndAmountIsGreaterThanEqual(requestVo.getSourceAccountId(), requestVo.getAmount());
        if (!sourceAccountOp.isPresent()){
            throw new RuntimeException("转账人账号不存在,或金额不足");
        }
        Optional<Account> targetAccountOp = accountRepository.findById(requestVo.getTargetAccountId());
        if (!targetAccountOp.isPresent()){
            throw new RuntimeException("收款人账号不存在");
        }

        // 从转账人账户扣款
        Account sourceAccount = sourceAccountOp.get();
        sourceAccount.setAmount(sourceAccount.getAmount()-requestVo.getAmount());
        accountRepository.save(sourceAccount);

        // 向目标账户打款
        Account targetAccount = targetAccountOp.get();
        targetAccount.setAmount(targetAccount.getAmount()+requestVo.getAmount());
        accountRepository.save(targetAccount);

        // 添加转账记录
    }

}
