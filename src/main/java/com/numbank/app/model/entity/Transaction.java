package com.numbank.app.model.entity;

import java.sql.Timestamp;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Integer id;
    private Double amount;
    private String label;
    private Timestamp dateEffect;
    private Timestamp saveDate;
    private Boolean extern;
    private Boolean status;
    private Integer accountIdSender;
    private Integer accountIdRecipient;
    private Integer categoryId;
}
