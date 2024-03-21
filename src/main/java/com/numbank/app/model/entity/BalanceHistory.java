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
    private String accountId;
    
    public BalanceHistory(Double balance, String accountId) {
        this.balance = balance;
        this.accountId = accountId;
    }
}
