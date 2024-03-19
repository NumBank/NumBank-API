package com.numbank.app.model.entity;

import java.sql.Timestamp;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BalacenHistory {
    private Integer id;
    private Double balance;
    private Timestamp updateDateTime;
    private Integer accountId;
}
