package com.numbank.app.model.entity;

import java.sql.Timestamp;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceHistory {
    private Integer id;
    private Double value;
    private Timestamp updateDateTime;
    private String accountId;
    
    public BalanceHistory(Double value, String accountId) {
        this.value = value;
        this.accountId = accountId;
    }
}
