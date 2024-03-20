package com.numbank.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Account;
import com.numbank.app.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository repo;

    public Account getById(Integer id) {
        return repo.getById(id);
    }

    public List<Account> getAll() {
        return repo.findAll();
    }

    public Account save(Account account) {
        return repo.save(account);
    }

    public List<Account> saveAll(List<Account> accounts) {
        return repo.saveAll(accounts);
    }
}
