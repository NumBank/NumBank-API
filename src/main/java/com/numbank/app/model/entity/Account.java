package com.numbank.app.model.entity;

import java.sql.Date;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer id;
    private String customerFirstName;
    private String customerLastName;
    private Date birthdate;
    private Double netSalary;
    private String number;
    private Boolean debtActivate;
}
