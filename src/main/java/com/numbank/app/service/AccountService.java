package com.numbank.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Account;
import com.numbank.app.model.entity.BalanceHistory;
import com.numbank.app.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository repo;
    private BalanceHistoryService balanceService;

    public Account getById(String id) {
        Account account = repo.getById(id);
        account.setBalance(balanceService.getBalanceByAccountId(id));
        return account;
    }

    public List<Account> getAll() {
        List<Account> allAccounts = repo.findAll();
        for (Account account : allAccounts) {
            account.setBalance(balanceService.getBalanceByAccountId(account.getId()));
        }
        return allAccounts;
    }

    public Account save(Account account) {
            account.setId(UUID.randomUUID().toString());
            account.setDebt(false);
            account.setBalance(0.0);
            repo.save(account);
            balanceService.save(new BalanceHistory(0.0, account.getId()));
        return account;
    }

    public List<Account> saveAll(List<Account> accounts) {
        List<Account> allAccountsSaved = new ArrayList<>();
        for (Account account : accounts) {
            save(account);
            allAccountsSaved.add(account);
        }
        return allAccountsSaved;
    }

    public Account update(Account account) {
        return repo.update(account);
    }
}
