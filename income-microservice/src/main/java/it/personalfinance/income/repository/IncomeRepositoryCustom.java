package it.personalfinance.income.repository;

import java.util.List;

import it.personalfinance.income.dto.IncomeSearchRequestDto;
import it.personalfinance.income.entity.Income;

public interface IncomeRepositoryCustom {
	public List<Income> search(IncomeSearchRequestDto dto);
}
