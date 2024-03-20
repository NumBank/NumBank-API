package com.numbank.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.numbank.app.model.entity.Transaction;
import com.numbank.app.service.TransactionService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private TransactionService service;

    @GetMapping({"", "/"})
    public List<Transaction> getTransactions() {
        return service.getAll();
    }

    @GetMapping({"/{id}"})
    public Transaction getTransactionById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }
    
    @PostMapping({"", "/"})
    public List<Transaction> saveTransactions(@RequestBody List<Transaction> transactions) {
        return service.saveAll(transactions);
    }
}
