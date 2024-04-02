package com.numbank.app.controller;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class DashboardController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/dashboard/amountsPerCategory")
    public List<Object[]> sumAmountsPerCategory(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        String sql = "SELECT c.name AS category, SUM(o.amount) AS sumOfAmount " +
                "FROM categorizeOperation co " +
                "JOIN category c ON co.idCategory = c.id " +
                "JOIN operation o ON co.idOperation = o.id " +
                "WHERE o.dateEffect BETWEEN ? AND ? " +
                "GROUP BY c.name";
        return jdbcTemplate.query(sql, new Object[]{startDate, endDate}, (rs, rowNum) -> new Object[]{rs.getString("category"), rs.getDouble("sumOfAmount")});
    }

    @GetMapping("/dashboard/expensesAndIncome")
    public Object[] sumExpensesAndIncome(@RequestParam LocalDateTime startDate,@RequestParam LocalDateTime endDate) {
        String sql = "SELECT SUM(CASE WHEN o.amount < 0 THEN o.amount ELSE 0 END) AS totalExpenses, " +
                "       SUM(CASE WHEN o.amount >= 0 THEN o.amount ELSE 0 END) AS totalIncome " +
                "FROM operation o " +
                "WHERE o.dateEffect BETWEEN ? AND ?";
        return jdbcTemplate.queryForList(sql,
                new Object[]{startDate, endDate},
                (rs, rowNum) -> new Object[]{rs.getDouble("totalExpenses"), rs.getDouble("totalIncome")});
    }
}
