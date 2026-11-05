package it.personalfinance.finance.repository;

import java.util.List;

import it.personalfinance.finance.dto.ExpenseSearchRequestDto;
import it.personalfinance.finance.entity.Expense;

public interface ExpenseRepositoryCustom {
	
	public List<Expense> search(ExpenseSearchRequestDto dto);
}
