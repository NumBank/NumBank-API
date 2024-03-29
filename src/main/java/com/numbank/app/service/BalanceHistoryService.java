package com.numbank.app.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<BalanceHistory> getAllByAccountId(String id, String startDateTime, String endDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql;

        if (startDateTime == null || endDateTime == null) {
            sql = "SELECT bh.* FROM \"account\" a INNER JOIN \"balancehistory\" bh ON bh.accountid = a.id " +
                "WHERE a.id = '" + id + "' " +
                "ORDER BY updatedatetime DESC ;";
        } else {
            LocalDateTime startDateTimeF = LocalDateTime.parse(startDateTime, formatter);
            LocalDateTime endDateTimeF = LocalDateTime.parse(endDateTime, formatter);

            sql = "SELECT bh.* FROM \"account\" a INNER JOIN \"balancehistory\" bh ON bh.accountid = a.id " +
                "WHERE a.id = '" + id + "' " +
                "AND updatedatetime BETWEEN '" + startDateTimeF.toString() + "' AND '" + endDateTimeF.toString() + "' " +
                "ORDER BY updatedatetime DESC ;";
        }

        return repo.findAllByAccountId(id, sql);
    }
}
