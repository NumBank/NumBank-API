package com.numbank.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numbank.app.model.entity.BalanceHistory;
import com.numbank.app.model.entity.MoneyDrawal;
import com.numbank.app.service.BalanceHistoryService;
import com.numbank.app.service.MoneyDrawalService;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
@RequestMapping("/balance")
public class BalanceController {
    private BalanceHistoryService serviceBalance;
    private MoneyDrawalService serviceMoneyDrawal;

    @GetMapping("/{accountid}")
    public List<BalanceHistory> getBalanceHistory(
        @PathVariable("accountid") String accountid,
        @PathParam("startDateTime") String startDateTime,
        @PathParam("endDateTime") String endDateTime
        ) {
        return serviceBalance.getAllByAccountId(accountid, startDateTime, endDateTime);
    }

    @GetMapping("/moneydrawal/{accountid}")
    public List<MoneyDrawal> getMoneyDrawal(
        @PathVariable("accountid") String accountid,
        @PathParam("startDateTime") String startDateTime,
        @PathParam("endDateTime") String endDateTime
        ) {
        return serviceMoneyDrawal.getAllByAccountId(accountid, startDateTime, endDateTime);
    }
    
}
