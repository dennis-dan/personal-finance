package it.personalfinance.income.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.personalfinance.income.repository.IncomeRepository;
import it.personalfinance.income.entity.Income;

@Service
public class IncomeService {
	private final IncomeRepository repository;
	
	public IncomeService(IncomeRepository repository) {
		this.repository = repository;
	}
	public List<Income> findAll(){
		return repository.findAll();
	}
	public Optional<Income> findById(String id){
		Optional<Income> result = repository.findById(id);
		if (!result.isEmpty()) return result;
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Income with id " + id + " not found");

	}
	
	public Income update(String id, Income income) {
		if (!findById(id).isEmpty()) {
			income.setId(id);
			return save(income);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Income with id " + id + " not found");
	}
	
	public String delete(String id) {
		if (!findById(id).isEmpty()) {
			repository.deleteById(id);
			return "Income_deleted!";
		}
		return null;
	}
	
	public Income save(Income income) {
	    // richiama il metodo save del repository
		Income result = repository.save(income);
	    // e restituisci il risultato
		return result;
	}
}
