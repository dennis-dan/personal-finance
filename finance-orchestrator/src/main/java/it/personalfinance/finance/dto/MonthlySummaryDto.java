package it.personalfinance.finance.dto;

import java.math.BigDecimal;

public class MonthlySummaryDto {

    private int year;
    private int month;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;

    public MonthlySummaryDto(int year, int month,
            BigDecimal totalIncome, BigDecimal totalExpense,
            BigDecimal balance) {
        this.year = year;
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}