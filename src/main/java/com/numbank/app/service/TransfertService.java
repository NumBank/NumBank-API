package com.numbank.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.numbank.app.model.entity.Account;
import com.numbank.app.repository.AccountRepository;
import com.numbank.app.repository.AutorizationOpenRepository;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Transaction;
import com.numbank.app.model.entity.Transfert;
import com.numbank.app.repository.TransfertRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransfertService {
    private TransfertRepository repo;
    private TransactionService transactionService;
    private AccountService accountService;

    public Transfert getById(String id) {
        return repo.getById(id);
    }

    public List<Transfert> getAll() {
        return repo.findAll();
    }

    public Transfert save(Transfert transfert) {
        transfert.setId(UUID.randomUUID().toString());
        if (transfert.getDateEffect() == null)
            transfert.setDateEffect(LocalDateTime.now());
        if (transfert.getSaveDate() == null)
            transfert.setSaveDate(LocalDateTime.now());
        return repo.save(transfert);
    }

    public List<Transfert> saveAll(List<Transfert> transferts) {
        List<Transfert> savedList = new ArrayList<>();
            for (Transfert transfert : savedList) {
                savedList.add(save(transfert));
            }
        return savedList;
    }

    public Transfert update(Transfert transfert) {
        Transfert transfertBefore = getById(transfert.getId());
        
        if (transfert.getStatus() == null || transfert.getStatus().equals(transfertBefore.getStatus()))
            transfert.setStatus(transfertBefore.getStatus());
        
        transfert.setAccountIdRecipient(transfertBefore.getAccountIdRecipient());
        transfert.setAccountIdSender(transfertBefore.getAccountIdSender());
        transfert.setAmount(transfertBefore.getAmount());
        transfert.setDateEffect(transfertBefore.getDateEffect());
        transfert.setSaveDate(transfertBefore.getSaveDate());
        transfert.setExtern(transfertBefore.getExtern());

        return repo.update(transfert);
    }

    public List<Transfert> saveAllTransferts(List<Transfert> transferts) {
        List<Transfert> savedList = new ArrayList<>();

        for (Transfert transfert : transferts) {
            Account accountSender = accountService.getById(transfert.getAccountIdSender());

            if (accountSender.getBalance() < transfert.getAmount()) {
                System.out.println("Transfer failed: balance not enough for account with number="+ accountSender.getNumber());
                return null;
            }
            savedList.add(save(transfert));

            transactionService.save(new Transaction(
                transfert.getAmount(),
                "DEBIT",
                transfert.getDateEffect(),
                transfert.getSaveDate(),
                transfert.getExtern(),
                transfert.getStatus(),
                transfert.getAccountIdSender(),
                2)
            );

            
            if (transfert.getExtern())
                transfert.setDateEffect(LocalDateTime.now().plusHours(48));
            
            transactionService.save(new Transaction(
                transfert.getAmount(),
                "CREDIT",
                transfert.getDateEffect(),
                transfert.getSaveDate(),
                !transfert.getExtern(),
                transfert.getStatus(),
                transfert.getAccountIdRecipient(),
                1)
            );
        }

        return savedList;
    }

}
