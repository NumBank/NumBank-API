package com.numbank.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Account;
import com.numbank.app.model.entity.Transaction;
import com.numbank.app.model.entity.Transfert;
import com.numbank.app.repository.TransactionRepository;
import com.numbank.app.repository.TransfertRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
    private TransactionRepository repo;
    private TransfertRepository transfertRepo;
    private AccountService accountService;

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
        if (validateTransaction(transaction))
            return repo.save(transaction);
        return null;
    }

    public List<Transaction> saveAll(List<Transaction> transactions) {
        List<Transaction> savedList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            savedList.add(save(transaction));
        }
        return savedList;
    }
    
    public List<Transfert> saveAllTransferts(List<Transfert> transferts) {
        List<Transfert> savedList = new ArrayList<>();
        for (Transfert transfert : transferts) {
            transfert.setId(UUID.randomUUID().toString());
            save(new Transaction(transfert.getAmount(), "DEBIT", null, null, null, null, transfert.getAccountIdSender(), 2));
            save(new Transaction(transfert.getAmount(), "CREDIT", null, null, null, null, transfert.getAccountIdRecipient(), 1));
            savedList.add(transfertRepo.save(transfert));
        }
        return savedList;
    }

    public Transaction update(Transaction transaction) {
        return repo.update(transaction);
    }

    private Boolean validateTransaction(Transaction transaction) {
        Account account = accountService.getById(transaction.getAccountId());
        Double balanceActually = account.getBalance();
        if (balanceActually < transaction.getAmount() && transaction.getLabel().equals("DEBIT")) {
            System.out.println("Transaction failed: balance not enough.");
            return false;
        }
        if (account.getDebt()) {
            System.out.println("Transaction failed: account not eligible to debt.");
            return false;
        }
        return true;
    }
}
