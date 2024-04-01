package com.numbank.app.service;

import com.numbank.app.model.entity.MoneyDrawal;
import com.numbank.app.repository.MoneyDrawalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        MoneyDrawal moneyDrawal = repo.getByAccountIdNow(id);
        if (moneyDrawal != null && moneyDrawal.getAmount() > 0.0) {
            Integer timeInterest = LocalDateTime.now().getDayOfMonth() - moneyDrawal.getWithDrawalDate().toLocalDateTime().getDayOfMonth();
            Double valueOfInterest = 0.0;

            if (timeInterest < 7 && timeInterest > 0) {
                valueOfInterest = moneyDrawal.getAmount() * 0.01;
            } else if (timeInterest >= 7) {
                valueOfInterest = moneyDrawal.getAmount() * 0.01 * (timeInterest - 6);
            }
            moneyDrawal.setAmount((moneyDrawal.getAmount() + valueOfInterest));
            return moneyDrawal;
        }
        return new MoneyDrawal();
    }

    public List<MoneyDrawal> getAllByAccountId(String id, String startDateTime, String endDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql;

        if (startDateTime == null || endDateTime == null) {
            sql = "SELECT mwd.* FROM \"account\" a INNER JOIN \"moneywithdrawal\" mwd ON mwd.accountid = a.id " +
                "WHERE a.id = '" + id + "' " +
                "ORDER BY withDrawalDate DESC;";
        } else {
            LocalDateTime startDateTimeF = LocalDateTime.parse(startDateTime, formatter);
            LocalDateTime endDateTimeF = LocalDateTime.parse(endDateTime, formatter);
            
            sql = "SELECT mwd.* FROM \"account\" a INNER JOIN \"moneywithdrawal\" mwd ON mwd.accountid = a.id " +
                "WHERE a.id = '" + id + "' " +
                "AND withDrawalDate BETWEEN '" + startDateTimeF.toString() + "' AND '" + endDateTimeF.toString() + "' " +
                "ORDER BY withDrawalDate DESC;";
        }

        return repo.findAllByAccountId(id, sql);
    }
}
