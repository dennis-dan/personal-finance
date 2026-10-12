package it.personalfinance.income.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.personalfinance.income.entity.Income;
import it.personalfinance.income.service.IncomeService;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
	private final IncomeService service;
	
	public IncomeController(IncomeService service) {
		this.service =service;
	}
	@GetMapping
	public List<Income> findAll(){
		return service.findAll();
	}
	
	@GetMapping(path="/{id}")
	public Optional<Income> findById(@PathVariable String id){
		return service.findById(id);
	}
	
	@PostMapping(path="/save")
	public Income save(@RequestBody Income income) {
		return service.save(income);
	}
	
	@PutMapping(path="/{id}")
	public Income update(@PathVariable String id, @RequestBody Income income) {
		return service.update(id, income);
	}
	
	@DeleteMapping(path="/{id}")
	public String delete(@PathVariable String id) {
		return service.delete(id);
	}

}
