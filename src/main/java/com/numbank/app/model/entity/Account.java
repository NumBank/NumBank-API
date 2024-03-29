package com.numbank.app.model.entity;

import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String id;
    private String customerFirstName;
    private String customerLastName;
    private LocalDate birthdate;
    private Double netSalary;
    private String number;
    private Boolean debt;
    private Double balance;

    public Account(String customerFirstName, String customerLastName, LocalDate birthdate, Double netSalary,
            String number, Boolean debt) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.birthdate = birthdate;
        this.netSalary = netSalary;
        this.number = number;
        this.debt = debt;
    }

    public Account(String customerFirstName, String customerLastName, LocalDate birthdate, Double netSalary,
            String number) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.birthdate = birthdate;
        this.netSalary = netSalary;
        this.number = number;
    }
}
