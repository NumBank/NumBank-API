package com.numbank.app.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.numbank.app.model.entity.BalanceHistory;
import com.numbank.app.model.entity.MoneyDrawal;
import com.numbank.app.repository.BalanceHistoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BalanceHistoryService {
    private BalanceHistoryRepository repo;
    private MoneyDrawalService serviceMoneyDrawal;

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

    public String getAllBalance(String accountId) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Double> balanceData = new HashMap<>();

        List<MoneyDrawal> allMoneyDrawal = serviceMoneyDrawal.getAllByAccountId(accountId, null, null);
        Double sumMoneyDrawal = 0.0;

        for (MoneyDrawal moneyDrawal : allMoneyDrawal) {
            sumMoneyDrawal = sumMoneyDrawal + moneyDrawal.getAmount();
        }

        balanceData.put("balance", getBalanceByAccountIdNow(accountId));
        balanceData.put("loan", sumMoneyDrawal);
        balanceData.put("loanInterest", sumMoneyDrawal + (sumMoneyDrawal * 0.07));

        String json = null;
        try {
            json = mapper.writeValueAsString(balanceData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
