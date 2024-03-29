package com.numbank.app.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.BalanceHistory;
import com.numbank.app.repository.BalanceHistoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BalanceHistoryService {
    private BalanceHistoryRepository repo;

    public BalanceHistory getById(String id) {
        return repo.getById(id);
    }

    public List<BalanceHistory> getAll() {
        return repo.findAll();
    }

    public BalanceHistory save(BalanceHistory balanceHistory) {
            balanceHistory.setUpdateDateTime(Timestamp.valueOf(LocalDateTime.now()));
        return repo.save(balanceHistory);
    }

    public Double getBalanceByAccountIdNow(String id) {
        return repo.getBalanceNow(id).getValue();
    }

    public List<BalanceHistory> getAllByAccountId(String id) {
        return repo.findAllByAccountId(id);
    }
}
