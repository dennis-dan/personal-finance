package it.personalfinance.finance.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import it.personalfinance.finance.entity.Expense;
import it.personalfinance.finance.repository.ExpenseRepository;

@Service
public class ExpenseService {
	
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

	public List<Expense> findAll() {
	    // chiama il metodo findAll() del repository
		List<Expense> result = expenseRepository.findAll(); 
	    // e restituisci il risultato
		return result;
	}
	
	public Optional<Expense> findById(String id) {
		Optional<Expense> result = expenseRepository.findById(id);
		if (!result.isEmpty()) return result;
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense with id " + id + " not found");
	}
	
	public Expense update(String id, Expense expense) {
		if (!findById(id).isEmpty()) {
			expense.setId(id);
			return save(expense);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense with id " + id + " not found");
	}
	
	public String delete(String id) {
		if (!findById(id).isEmpty()) {
			expenseRepository.deleteById(id);
			return "Expense_deleted!";
		}
		return null;
	}
	
	public Expense save(Expense expense) {
	    // richiama il metodo save del repository
		Expense result = expenseRepository.save(expense);
	    // e restituisci il risultato
		return result;
	}
}
