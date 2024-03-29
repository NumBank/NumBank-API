package com.numbank.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numbank.app.model.entity.Account;
import com.numbank.app.service.AccountService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private AccountService service;

    @GetMapping({"", "/"})
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = service.getAll();
        if (accounts == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(accounts);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Account> getAccountById(@PathVariable("id") String id) {
        Account account = service.getById(id);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(account);
    }
    
    @PostMapping({"", "/"})
    public ResponseEntity<List<Account>> saveAccounts(@RequestBody List<Account> accounts) {
        List<Account> accountSaved = service.saveAll(accounts);
        if (accountSaved == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(accountSaved);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Account> updateAccount(@PathVariable String id, @RequestBody Account account) {
        Account accountUpdated = service.update(account);
        if (accountUpdated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(accountUpdated);
    }
}
