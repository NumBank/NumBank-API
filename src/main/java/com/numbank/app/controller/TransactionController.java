package com.numbank.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.numbank.app.model.entity.Transaction;
import com.numbank.app.model.entity.Transfert;
import com.numbank.app.service.TransactionService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


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
    public Transaction getTransactionById(@PathVariable("id") String id) {
        return service.getById(id);
    }

    @GetMapping({"/account/{accountId}"})
    public List<Transaction> getTransactionByAccountId(@PathVariable("accountId") String accountId) {
        return service.getByAccountId(accountId);
    }
    
    @PostMapping({"/supply"})
    public List<Transaction> saveTransactions(@RequestBody List<Transaction> transactions) {
        return service.saveAll(transactions);
    }
    
    @PostMapping({"/transfert"})
    public List<Transfert> saveTransfert(@RequestBody List<Transfert> transferts) {
        return service.saveAllTransferts(transferts);
    }

    @PutMapping({"/{id}"})
    public Transaction updateTransaction(@PathVariable String id, @RequestBody Transaction transaction) {
        return service.update(transaction);
    }
}
