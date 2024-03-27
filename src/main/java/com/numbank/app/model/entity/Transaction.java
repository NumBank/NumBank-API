package com.numbank.app.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;
    private Double amount;
    private String label;
    private LocalDateTime dateEffect;
    private LocalDateTime saveDate;
    private Boolean extern;
    private Boolean status;
    private String accountId;
    private String categoryId;
    
    public Transaction(Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate, Boolean extern,
            Boolean status, String accountId, String categoryId) {
        this.amount = amount;
        this.label = label;
        this.dateEffect = dateEffect;
        this.saveDate = saveDate;
        this.extern = extern;
        this.status = status;
        this.categoryId = categoryId;
        this.accountId = accountId;
    }

    public Transaction(Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate, Boolean extern,
            Boolean status, String categoryId) {
        this.amount = amount;
        this.label = label;
        this.dateEffect = dateEffect;
        this.saveDate = saveDate;
        this.extern = extern;
        this.status = status;
        this.categoryId = categoryId;
    }

    public Transaction(String id, Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate,
            Boolean extern, Boolean status, String categoryId) {
        this.id = id;
        this.amount = amount;
        this.label = label;
        this.dateEffect = dateEffect;
        this.saveDate = saveDate;
        this.extern = extern;
        this.status = status;
        this.categoryId = categoryId;
    }
}
