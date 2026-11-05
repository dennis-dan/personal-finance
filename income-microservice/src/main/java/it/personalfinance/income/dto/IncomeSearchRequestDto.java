package it.personalfinance.income.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeSearchRequestDto {
    private String description;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
}
