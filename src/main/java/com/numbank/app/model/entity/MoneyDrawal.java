package com.numbank.app.model.entity;

import java.sql.Timestamp;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyDrawal {
    private Integer id;
    private Double amount;
    private Timestamp withDrawalDate;
    private String accountId;

    public MoneyDrawal(Double amount, Timestamp withDrawalDate, String accountId) {
        this.amount = amount;
        this.withDrawalDate = withDrawalDate;
        this.accountId = accountId;
    }
}
