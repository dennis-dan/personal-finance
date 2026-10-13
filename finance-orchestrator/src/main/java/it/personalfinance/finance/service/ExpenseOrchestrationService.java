package it.personalfinance.finance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.personalfinance.finance.client.FinanceMicroserviceClient;
import it.personalfinance.finance.dto.ExpenseRequestDto;
import it.personalfinance.finance.dto.ExpenseResponseDto;

@Service
public class ExpenseOrchestrationService {
	
	private final FinanceMicroserviceClient client;
	
	public ExpenseOrchestrationService(FinanceMicroserviceClient client) {
		this.client=client;
	}
	
	public List<ExpenseResponseDto> findAll(){
		return client.findAll();
	}

	public ExpenseResponseDto findById(String id) {
		return client.findById(id);
	}

	public ExpenseResponseDto update(String id, ExpenseRequestDto expense) {
		return client.update(id,expense);
	}

	public String delete(String id) {
		return client.delete(id);
	}

	public ExpenseResponseDto save(ExpenseRequestDto expense) {
		return client.save(expense);
	}
}
