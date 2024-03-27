package com.numbank.app.model.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transfert extends Transaction {
    private String accountIdSender;
    private String accountIdRecipient;

    public Transfert(Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate, Boolean extern,
                     Boolean status, String accountIdSender, String accountIdRecipient, String categoryId) {
        super(amount, label, dateEffect, saveDate, extern, status, categoryId);
        this.accountIdSender = accountIdSender;
        this.accountIdRecipient = accountIdRecipient;
    }

    public Transfert(String id, Double amount, String label, LocalDateTime dateEffect, LocalDateTime saveDate, Boolean extern,
            Boolean status, String accountIdSender, String accountIdRecipient, String categoryId) {
        super(id, amount, label, dateEffect, saveDate, extern, status, categoryId);
        this.accountIdSender = accountIdSender;
        this.accountIdRecipient = accountIdRecipient;
    }
}
