package com.numbank.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Transaction;
import com.numbank.app.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
    private TransactionRepository repo;

    public Transaction getById(String id) {
        return repo.getById(id);
    }

    public List<Transaction> getAll() {
        return repo.findAll();
    }
    
    public Transaction save(Transaction transaction) {
            transaction.setId(UUID.randomUUID().toString());
        return repo.save(transaction);
    }
    
    public List<Transaction> saveAll(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transaction.setId(UUID.randomUUID().toString());
        }
        return repo.saveAll(transactions);
    }

    public Transaction update(Transaction transaction) {
        return repo.update(transaction);
    }
}
