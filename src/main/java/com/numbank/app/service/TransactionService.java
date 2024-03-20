package com.numbank.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Transaction;
import com.numbank.app.repository.TransactionRepository;

@Service
public class TransactionService {
    private TransactionRepository repo;

    public Transaction getById(Integer id) {
        return repo.getById(id);
    }

    public List<Transaction> getAll() {
        return repo.findAll();
    }
    
    public Transaction save(Transaction transaction) {
        return repo.save(transaction);
    }
    
    public List<Transaction> saveAll(List<Transaction> transactions) {
        return repo.saveAll(transactions);
    }
}
