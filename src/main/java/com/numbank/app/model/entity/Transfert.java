package com.numbank.app.model.entity;

import lombok.*;

import java.time.LocalDateTime;

import com.numbank.app.model.ModelTransaction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transfert extends ModelTransaction {
    private String accountIdSender;
    private String accountIdRecipient;

    public Transfert(Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate, Boolean extern, Boolean status, String accountIdSender, String accountIdRecipient) {
        super(accountIdRecipient, amount, label, dateEffect, saveDate, extern, status);
        this.accountIdSender = accountIdSender;
        this.accountIdRecipient = accountIdRecipient;
    }

    public Transfert(String id, Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate, Boolean extern, Boolean status, String accountIdSender, String accountIdRecipient) {
        super(id, amount, label, dateEffect, saveDate, extern, status);
        this.accountIdSender = accountIdSender;
        this.accountIdRecipient = accountIdRecipient;
    }
}
