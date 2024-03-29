package com.numbank.app.service;

import com.numbank.app.model.entity.MoneyDrawal;
import com.numbank.app.repository.MoneyDrawalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MoneyDrawalService {
    private MoneyDrawalRepository repo;

    public MoneyDrawal getById(Integer id) {
        return repo.getById(id);
    }

    public List<MoneyDrawal> getAll() {
        return repo.findAll();
    }

    public MoneyDrawal save(MoneyDrawal moneyDrawal) {
        moneyDrawal.setWithDrawalDate(Timestamp.valueOf(LocalDateTime.now()));
        return repo.save(moneyDrawal);
    }

    public MoneyDrawal getMoneyDrawalByAccountIdNow(String id) {
        return repo.getByAccountIdNow(id);
    }

    public List<MoneyDrawal> getAllByAccountId(String id) {
        return repo.findAllByAccountId(id);
    }
}
