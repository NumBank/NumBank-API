package com.numbank.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Account;
import com.numbank.app.model.entity.MoneyDrawal;
import com.numbank.app.model.entity.Transaction;
import com.numbank.app.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
    private TransactionRepository repo;
    private AccountService accountService;
    private MoneyDrawalService moneyDrawalService;

    public Transaction getById(String id) {
        return repo.getById(id);
    }

    public List<Transaction> getAll() {
        return repo.findAll();
    }

    public List<Transaction> getByAccountId(String accountId) {
        List<Transaction> allTransactions = repo.findAll();
        return allTransactions.stream().filter(transaction -> transaction.getAccountId().equals(accountId)).toList();
    }
    
    public Transaction save(Transaction transaction) {
        transaction.setId(UUID.randomUUID().toString());
        if (transaction.getDateEffect() == null && transaction.getSaveDate() == null) {
            transaction.setDateEffect(LocalDateTime.now());
            transaction.setSaveDate(LocalDateTime.now());
        }
        return validateTransaction(transaction);
    }

    public List<Transaction> saveAll(List<Transaction> transactions) {
        List<Transaction> savedList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            savedList.add(save(transaction));
        }
        return savedList;
    }
    
    public Transaction update(Transaction transaction) {
        return repo.update(transaction);
    }

    private Transaction validateTransaction(Transaction transaction) {
        Account account = accountService.getById(transaction.getAccountId());
        Double balanceActually = account.getBalance();

        if (balanceActually < transaction.getAmount() && transaction.getLabel().equals("DEBIT")) {

            if (account.getDebt()) {
                System.out.println("Transaction success: account eligible to debt.");
                moneyDrawalService.save(new MoneyDrawal((transaction.getAmount() - balanceActually), null, transaction.getAccountId()));
                return repo.save(transaction);
            }

            System.out.println("Transaction failed: balance not enough or account not eligible to debt");
            return null;
        }

        return repo.save(transaction);
    }
}
