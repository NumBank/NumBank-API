package com.numbank.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Account;
import com.numbank.app.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository repo;

    public Account getById(String id) {
        return repo.getById(id);
    }

    public List<Account> getAll() {
        return repo.findAll();
    }

    public Account save(Account account) {
            account.setId(UUID.randomUUID().toString());
            account.setDebt(false);
        return repo.save(account);
    }

    public List<Account> saveAll(List<Account> accounts) {
        for (Account account : accounts) {
            account.setId(UUID.randomUUID().toString());
            account.setDebt(false);
        }
        return repo.saveAll(accounts);
    }

    public Account update(Account account) {
        return repo.update(account);
    }
}
