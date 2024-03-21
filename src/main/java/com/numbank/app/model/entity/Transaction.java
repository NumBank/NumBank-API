package com.numbank.app.model.entity;

import java.sql.Timestamp;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;
    private Double amount;
    private String label;
    private Timestamp dateEffect;
    private Timestamp saveDate;
    private Boolean extern;
    private Boolean status;
    private String accountIdSender;
    private String accountIdRecipient;
    private String categoryId;
    
    public Transaction(Double amount, String label, Timestamp dateEffect, Timestamp saveDate, Boolean extern,
            Boolean status, String accountIdSender, String accountIdRecipient, String categoryId) {
        this.amount = amount;
        this.label = label;
        this.dateEffect = dateEffect;
        this.saveDate = saveDate;
        this.extern = extern;
        this.status = status;
        this.accountIdSender = accountIdSender;
        this.accountIdRecipient = accountIdRecipient;
        this.categoryId = categoryId;
    }
}
