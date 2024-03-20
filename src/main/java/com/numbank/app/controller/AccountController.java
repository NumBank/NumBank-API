package com.numbank.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numbank.app.model.entity.Account;
import com.numbank.app.service.AccountService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private AccountService service;

    @GetMapping({"", "/"})
    public List<Account> getAccounts() {
        return service.getAll();
    }

    @GetMapping({"/{id}"})
    public Account getAccountById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }
    
    @PostMapping({"", "/"})
    public List<Account> saveAccounts(@RequestBody List<Account> accounts) {
        return service.saveAll(accounts);
    }
}
