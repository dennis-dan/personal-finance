package it.personalfinance.finance.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import it.personalfinance.finance.dto.ExpenseSearchRequestDto;
import it.personalfinance.finance.dto.IncomeSearchRequestDto;
import it.personalfinance.finance.dto.ExpenseResponseDto;
import it.personalfinance.finance.dto.IncomeResponseDto;
import it.personalfinance.finance.dto.MonthlySummaryDto;

@Service
public class MonthlySummaryService {

    private final ExpenseOrchestrationService expenseService;
    private final IncomeOrchestrationService incomeService;

    public MonthlySummaryService(
            ExpenseOrchestrationService expenseService,
            IncomeOrchestrationService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    public MonthlySummaryDto getMonthlySummary(int year, int month) {

        // Individuo il primo e l'ultimo giorno del mese
        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // Preparo i filtri per le spese
        ExpenseSearchRequestDto expenseRequest = new ExpenseSearchRequestDto();
        expenseRequest.setStartDate(startDate);
        expenseRequest.setEndDate(endDate);

        // Preparo i filtri per le entrate
        IncomeSearchRequestDto incomeRequest = new IncomeSearchRequestDto();
        incomeRequest.setStartDate(startDate);
        incomeRequest.setEndDate(endDate);

        // Recupero i dati dai due microservizi
        List<ExpenseResponseDto> expenses = expenseService.search(expenseRequest);
        List<IncomeResponseDto> incomes = incomeService.search(incomeRequest);

        // Calcolo il totale delle spese
        BigDecimal totalExpense = expenses.stream()
                .map(ExpenseResponseDto::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcolo il totale delle entrate
        BigDecimal totalIncome = incomes.stream()
                .map(IncomeResponseDto::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcolo il saldo
        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new MonthlySummaryDto(
                year,
                month,
                totalIncome,
                totalExpense,
                balance
        );
    }
}
