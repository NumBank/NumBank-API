package com.numbank.app.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.numbank.app.model.entity.MoneyDrawal;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.numbank.app.model.entity.BalanceHistory;
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
        BalanceHistory balanceHistory = repo.getBalanceNow(id);
        MoneyDrawal moneyDrawal = serviceMoneyDrawal.getMoneyDrawalByAccountIdNow(id);
        if (moneyDrawal != null && moneyDrawal.getAmount() > Math.abs(balanceHistory.getValue())) {
            balanceHistory.setValue(-moneyDrawal.getAmount());
            return balanceHistory.getValue();
        }
        return balanceHistory.getValue();
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
                "AND updatedatetime BETWEEN '" + startDateTime + "' AND '" + endDateTime + "' " +
                "ORDER BY updatedatetime DESC ;";
        }
        return repo.findAllByAccountId(id, sql);
    }

    public String getAllBalance(String accountId) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Double> balanceData = new HashMap<>();
        Double balance = 0.0;
        Double loan = 0.0;
        Double loanInterest = 0.01;

        MoneyDrawal moneyDrawal =  serviceMoneyDrawal.getMoneyDrawalByAccountIdNow(accountId);
        if (moneyDrawal.getAmount() != 0.0) {
            Double valueOfInterest = getValueOfInterest(moneyDrawal);
            loan = moneyDrawal.getAmount() * valueOfInterest;
            loanInterest = valueOfInterest;
            balance = getBalanceByAccountIdNow(accountId) +(-loan);
        }

        balanceData.put("balance", balance);
        balanceData.put("loan", loan);
        balanceData.put("loanInterest", loanInterest);

        String json = null;
        try {
            json = mapper.writeValueAsString(balanceData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    private Double getValueOfInterest(MoneyDrawal moneyDrawal) {
        /*
         Day of different month fix it
         -----------------------------
         -----------------------------
         */
        Integer timeInterest = LocalDateTime.now().getDayOfMonth() - moneyDrawal.getWithDrawalDate().toLocalDateTime().getDayOfMonth();
        Double valueOfInterest = 0.01;

        if (timeInterest < 7 && timeInterest > 0) {
            valueOfInterest = moneyDrawal.getAmount() * 0.01;
        } else if (timeInterest >= 7) {
            valueOfInterest = moneyDrawal.getAmount() * 0.01 * (timeInterest - 6);
        }
        return valueOfInterest;
    }
}
