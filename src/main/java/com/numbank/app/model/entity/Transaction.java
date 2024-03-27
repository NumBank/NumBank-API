package com.numbank.app.model.entity;

import java.time.LocalDateTime;

import com.numbank.app.model.ModelTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transaction extends ModelTransaction {
    private String accountId;
    private Integer categoryId;
    
    public Transaction(Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate, Boolean extern,
            Boolean status, String accountId, Integer categoryId) {
        super(amount, label, dateEffect, saveDate, extern, status);
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public Transaction(String id, Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate,
            Boolean extern, Boolean status, String accountId, Integer categoryId) {
        super(id, amount, label, dateEffect, saveDate, extern, status);
        this.accountId = accountId;
        this.categoryId = categoryId;
    }
    
}
