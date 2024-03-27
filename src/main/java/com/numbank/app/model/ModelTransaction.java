package com.numbank.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelTransaction {
    private String id;
    private Double amount;
    private String label;
    private LocalDateTime dateEffect;
    private LocalDateTime saveDate;
    private Boolean extern;
    private Boolean status;
    
    public ModelTransaction(Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate,
            Boolean extern, Boolean status) {
        this.amount = amount;
        this.label = label;
        this.dateEffect = dateEffect;
        this.saveDate = saveDate;
        this.extern = extern;
        this.status = status;
    }
}
