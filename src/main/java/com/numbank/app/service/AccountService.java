package com.numbank.app.service;

import java.time.LocalDate;
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
        account.setBalance(balanceService.getBalanceByAccountIdNow(id));
        return account;
    }

    public List<Account> getAll() {
        List<Account> allAccounts = repo.findAll();
        for (Account account : allAccounts) {
            account.setBalance(balanceService.getBalanceByAccountIdNow(account.getId()));
        }
        return allAccounts;
    }

    public Account save(Account account) {
            account.setId(UUID.randomUUID().toString());
            account.setDebt(false);
            account.setBalance(0.0);

        if ((LocalDate.now().getYear() - account.getBirthdate().getYear()) <= 21) {
                System.out.println("Age non valide");
            return null;
        } else {
                repo.save(account);
                balanceService.save(new BalanceHistory(0.0, account.getId()));
            return account;
        }
    }

    public List<Account> saveAll(List<Account> accounts) {
        List<Account> allAccountsSaved = new ArrayList<>();
        for (Account account : accounts) {
            allAccountsSaved.add(save(account));
        }
        return allAccountsSaved;
    }

    public Account update(Account account) {
        Account accountBefore = getById(account.getId());
        
        if (account.getCustomerFirstName() == null || account.getCustomerFirstName().equals(accountBefore.getCustomerFirstName())) {
            account.setCustomerFirstName(accountBefore.getCustomerFirstName());
        }
        
        if (account.getCustomerLastName() == null || account.getCustomerLastName().equals(accountBefore.getCustomerLastName())) {
            account.setCustomerLastName(accountBefore.getCustomerLastName());
        }
        
        if (account.getBirthdate() == null || account.getBirthdate().equals(accountBefore.getBirthdate())) {
            account.setBirthdate(accountBefore.getBirthdate());
        }
        
        if (account.getNetSalary() == null || account.getNetSalary().equals(accountBefore.getNetSalary())) {
            account.setNetSalary(accountBefore.getNetSalary());
        }
        
        if (account.getDebt() == null || account.getDebt().equals(accountBefore.getDebt())) {
            account.setDebt(accountBefore.getDebt());
        }
        
        account.setNumber(accountBefore.getNumber());
        account.setBalance(accountBefore.getBalance());

        repo.update(account);
        return account;
    }
}
