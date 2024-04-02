package com.numbank.app.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import com.numbank.app.model.entity.BalanceHistory;
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
    private BalanceHistoryService balanceHistoryService;
    private MoneyDrawalService moneyDrawalService;
    private CategoryService categoryService;

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
        Account account = accountService.getById(transaction.getAccountId());
        if (account == null) {
            System.out.println("Account (with id=" + transaction.getAccountId() + ") not exist");
            return null;
        }

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
        // return repo.update(transaction);
        return transaction;
    }

    private Transaction validateTransaction(Transaction transaction) {
        Account account = accountService.getById(transaction.getAccountId());
        Double balanceActuallyValue = account.getBalance();
        MoneyDrawal moneyDrawalActually = moneyDrawalService.getMoneyDrawalByAccountIdNow(transaction.getAccountId());
        Double moneyDrawalActuallyValue = 0.0;

        if (moneyDrawalActually != null)
            moneyDrawalActuallyValue = moneyDrawalActually.getAmount();

        if (balanceActuallyValue != null &&
            transaction.getLabel().equals("DEBIT")
            ) {

            if (balanceActuallyValue < transaction.getAmount() &&
                transaction.getAmount() > (account.getNetSalary()/3) ||
                moneyDrawalActuallyValue > (account.getNetSalary()/3)
                ) {
                System.out.println("Transaction failed: balance not enough for account with id=" + account.getNumber());
                return null;
            }

            if (balanceActuallyValue < transaction.getAmount() && account.getDebt()) {
                Double restOfMoneyDrawal = (moneyDrawalActuallyValue + (transaction.getAmount() - balanceActuallyValue));
                repo.save(transaction);

                moneyDrawalService.save(new MoneyDrawal(
                    restOfMoneyDrawal,
                    null,
                    transaction.getAccountId())
                );
                return transaction;

            } else if (balanceActuallyValue < transaction.getAmount() && !account.getDebt()) {
                System.out.println("Transaction failed: account with number=" + account.getNumber() + " not eligible to debt");
                return null;

            } else {
                repo.save(transaction);
                return transaction;
            }
        }

        if (balanceActuallyValue != null &&
            balanceActuallyValue < 0.0 &&
            moneyDrawalActuallyValue != 0.0 &&
            transaction.getLabel().equals("CREDIT")) {
            double restOfMoneyDrawal = (transaction.getAmount() - moneyDrawalActuallyValue);
            MoneyDrawal moneyDrawalToSaved = new MoneyDrawal(0.0, Timestamp.valueOf(LocalDateTime.now().plusSeconds(2)), transaction.getAccountId());
                if (restOfMoneyDrawal <= 0) {
                    moneyDrawalToSaved.setAmount(Math.abs(restOfMoneyDrawal));
                }
            repo.save(transaction);
            moneyDrawalService.save(moneyDrawalToSaved);
        }

        if (moneyDrawalActuallyValue == 0.0 &&
            transaction.getLabel().equals("CREDIT")) {
            repo.save(transaction);
        }

        return transaction;
    }

    public List<Map<String, Object>> accountStatements(String id) {
        List<Map<String, Object>> allStatements = new ArrayList<>();
        List<Transaction> transactionList = getByAccountId(id);

        for (Transaction transaction : transactionList) {
            Map<String, Object> statement = new HashMap<>();

            Double valueCredit = 0.0;
            Double valueDebit = 0.0;
            LocalDateTime date = transaction.getSaveDate();
            BalanceHistory balanceHistory = balanceHistoryService.getBalanceBetweenTwoDate(transaction.getAccountId(), date);

            if (Objects.equals(transaction.getLabel(), "CREDIT"))
                valueCredit = transaction.getAmount();
            if (Objects.equals(transaction.getLabel(), "DEBIT"))
                valueDebit = transaction.getAmount();

            statement.put("Date", transaction.getDateEffect().toLocalDate().toString().replace('-', '/'));
            statement.put("Ref", "VIR_".concat(transaction.getSaveDate().toLocalDate().toString().replace('-', '_')));
            statement.put("Motif", categoryService.getById(transaction.getCategoryId()).getName());
            statement.put("Credit MGA", valueCredit);
            statement.put("Debit MGA", valueDebit);
            statement.put("Solde", balanceHistory.getValue());

            allStatements.add(statement);

        }

        return allStatements;
    }
}
