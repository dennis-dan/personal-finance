package it.personalfinance.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSearchRequestDto {

    private String description;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
}