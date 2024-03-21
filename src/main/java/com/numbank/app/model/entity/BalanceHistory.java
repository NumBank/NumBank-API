package com.numbank.app.model.entity;

import java.sql.Timestamp;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceHistory {
    private String id;
    private Double balance;
    private Timestamp updateDateTime;
    private Integer accountId;
    
    public BalanceHistory(Double balance, Integer accountId) {
        this.balance = balance;
        this.accountId = accountId;
    }
}
