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
        return repo.save(validateTransaction(transaction));
        // return validateTransaction(transaction);
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
        Double balanceActuallyValue = account.getBalance();
        MoneyDrawal moneyDrawalActually = moneyDrawalService.getMoneyDrawalByAccountIdNow(transaction.getAccountId());
        Double moneyDrawalActuallyValue = 0.0;

        if (moneyDrawalActually != null)
            moneyDrawalActuallyValue = moneyDrawalActually.getAmount();

        if (balanceActuallyValue != null &&
            balanceActuallyValue < transaction.getAmount() &&
            transaction.getLabel().equals("DEBIT")) {

            if (account.getDebt()) {
                Double restofMoneyDrawal = (moneyDrawalActuallyValue + (transaction.getAmount() - balanceActuallyValue));
                moneyDrawalService.save(new MoneyDrawal(
                    restofMoneyDrawal,
                    null,
                    transaction.getAccountId())
                );

                return transaction;
            }

            System.out.println("Transaction failed: balance not enough or account not eligible to debt");
            return null;
        }

        if (balanceActuallyValue != null &&
            balanceActuallyValue < 0.0 &&
            moneyDrawalActuallyValue != 0.0 &&
            transaction.getLabel().equals("CREDIT")) {
            Double restofMoneyDrawal = (transaction.getAmount() - moneyDrawalActuallyValue);

            moneyDrawalService.save(new MoneyDrawal(
                Math.abs(restofMoneyDrawal),
                null,
                transaction.getAccountId())
            );
        }

        return transaction;
    }
}
