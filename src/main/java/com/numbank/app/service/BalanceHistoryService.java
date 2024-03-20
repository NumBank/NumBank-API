package com.numbank.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.BalanceHistory;
import com.numbank.app.repository.BalanceHistoryRepository;

@Service
public class BalanceHistoryService {
    private BalanceHistoryRepository repo;

    public BalanceHistory getById(Integer id) {
        return repo.getById(id);
    }

    public List<BalanceHistory> getAll() {
        return repo.findAll();
    }

    public BalanceHistory save(BalanceHistory balanceHistory) {
        return repo.save(balanceHistory);
    }

    public List<BalanceHistory> saveAll(List<BalanceHistory> balanceHistories) {
        return repo.saveAll(balanceHistories);
    }
}
