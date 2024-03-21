package com.numbank.app.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.BalanceHistory;
import com.numbank.app.repository.BalanceHistoryRepository;

@Service
public class BalanceHistoryService {
    private BalanceHistoryRepository repo;

    public BalanceHistory getById(String id) {
        return repo.getById(id);
    }

    public List<BalanceHistory> getAll() {
        return repo.findAll();
    }

    public BalanceHistory save(BalanceHistory balanceHistory) {
            balanceHistory.setId(UUID.randomUUID().toString());
            balanceHistory.setUpdateDateTime(Timestamp.valueOf(LocalDateTime.now()));
        return repo.save(balanceHistory);
    }

    public List<BalanceHistory> saveAll(List<BalanceHistory> balanceHistories) {
        for (BalanceHistory balanceHistory : balanceHistories) {
            balanceHistory.setId(UUID.randomUUID().toString());
            balanceHistory.setUpdateDateTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        return repo.saveAll(balanceHistories);
    }
}
